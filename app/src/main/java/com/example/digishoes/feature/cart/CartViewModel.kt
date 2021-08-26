package com.example.digishoes.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.DigiViewModel
import com.example.digishoes.common.asyncNetwork
import com.example.digishoes.data.CartItem
import com.example.digishoes.data.CartResponse
import com.example.digishoes.data.PurchaseDetail
import com.example.digishoes.data.repo.CartRepository
import io.reactivex.Completable

class CartViewModel(val cartRepository: CartRepository) : DigiViewModel() {
    val cartItemLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()

    private fun getItemCart() {
        progressBar.value = true

        cartRepository.get()
            .asyncNetwork()
            .doFinally {
                progressBar.value = false
            }
            .subscribe(object : DigiSingleObserver<CartResponse>(compositeDisposable) {
                override fun onSuccess(t: CartResponse) {
                    cartItemLiveData.value = t.cart_items
                    purchaseDetailLiveData.value =
                        PurchaseDetail(t.payable_price, t.shipping_cost, t.total_price)
                }
            })
    }

    fun removeItem(cartItem: CartItem): Completable {
        return cartRepository.removeCart(cartItem.cart_item_id).ignoreElement()
    }

    fun increaseCartCount(cartItem: CartItem): Completable {
        return cartRepository.changeItemCount(cartItem.cart_item_id, ++cartItem.count)
            .ignoreElement()
    }

    fun decreaseCartCount(cartItem: CartItem): Completable {
        return cartRepository.changeItemCount(cartItem.cart_item_id, --cartItem.count)
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