package com.example.digishoes.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.R
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.DigiViewModel
import com.example.digishoes.common.asyncNetwork
import com.example.digishoes.data.*
import com.example.digishoes.data.repo.CartRepository
import com.example.digishoes.feature.main.MainViewModel
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class CartViewModel(val cartRepository: CartRepository) : DigiViewModel() {
    val cartItemLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    private fun getItemCart() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBar.value = true
            emptyStateLiveData.value = EmptyState(false)
            cartRepository.get()
                .asyncNetwork()
                .doFinally {
                    progressBar.value = false
                }
                .subscribe(object : DigiSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.payable_price, t.shipping_cost, t.total_price)
                        } else {
                            emptyStateLiveData.value =
                                EmptyState(true, R.string.emptyState, image = true)
                        }
                    }
                })
        } else {
            emptyStateLiveData.value = EmptyState(true, R.string.emptyStateLoginMessage, true)
        }
    }

    fun removeItem(cartItem: CartItem): Completable {
        return cartRepository.removeCart(cartItem.cart_item_id)
            .doAfterSuccess {
                Timber.i("cart Item count size ${cartItemLiveData.value?.size}")
                calculatePricePurchaseDetail()

                val badge = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                badge?.let {
                    it.count -= cartItem.count
                    EventBus.getDefault().postSticky(it)
                }

                cartItemLiveData.value?.let {
                    if (it.isEmpty()) {
                        emptyStateLiveData.postValue(
                            EmptyState(
                                true,
                                R.string.emptyState,
                                image = true
                            )
                        )

                    }
                }
            }
            .ignoreElement()

    }

    fun increaseCartCount(cartItem: CartItem): Completable {
        return cartRepository.changeItemCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess {
                calculatePricePurchaseDetail()
                val badge = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                badge?.let {
                    it.count += 1
                    EventBus.getDefault().postSticky(it)
                }
            }
            .ignoreElement()
    }

    fun decreaseCartCount(cartItem: CartItem): Completable {
        return cartRepository.changeItemCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess {
                calculatePricePurchaseDetail()
                val badge = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                badge?.let {
                    it.count -= 1
                    EventBus.getDefault().postSticky(it)
                }
            }
            .ignoreElement()
    }

    fun refresh() {
        getItemCart()
    }

    fun calculatePricePurchaseDetail() {
        cartItemLiveData.value?.let { cartItems ->
            purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }
                purchaseDetail.total_price = totalPrice
                purchaseDetail.payable_price = payablePrice

                purchaseDetailLiveData.postValue(purchaseDetail)
            }

        }
    }
}