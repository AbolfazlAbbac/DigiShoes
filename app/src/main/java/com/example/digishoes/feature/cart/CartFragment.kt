package com.example.digishoes.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.DigiCompletableObserver
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.common.NikeFragment
import com.example.digishoes.data.CartItem
import com.example.digishoes.feature.auth.AuthActivity
import com.example.digishoes.feature.auth.FragmentLogin
import com.example.digishoes.feature.common.CartItemAdapter
import com.example.digishoes.feature.shipping.ShippingActivity
import com.example.digishoes.product.ProductDetails
import com.example.digishoes.service.ImageLoadingService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.empty_state_view.*
import kotlinx.android.synthetic.main.empty_state_view.view.*
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class CartFragment : NikeFragment(), CartItemAdapter.CartItemListener {
    val viewModel: CartViewModel by inject()
    var cartAdapter: CartItemAdapter? = null
    val imageLoadingService: ImageLoadingService by inject()
    val compositeDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBar.observe(viewLifecycleOwner) {
            setProgressbarIndicator(it)
        }

        viewModel.cartItemLiveData.observe(viewLifecycleOwner) {
            Timber.i("Cart Item -> $it")
            cartProductRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            cartAdapter = CartItemAdapter(
                it as MutableList<CartItem>,
                requireContext(),
                imageLoadingService,
                this
            )
            cartProductRv.adapter = cartAdapter
        }

        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner) {
            Timber.i("Purchase Detail -> $it")
            cartAdapter?.let { adapter ->
                adapter.purchaseDetail = it
                adapter.notifyItemChanged(adapter.cartItems.size)
            }
        }

        viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {
            if (it.mostShow) {
                val emptyState = showEmptyState(R.layout.empty_state_view)
                emptyState?.let { view ->
                    view.messageEmptyState.text = getString(it.textEmptyState)
                    view.CbnBtn.visibility = if (it.Cba) View.VISIBLE else View.GONE
                    view.CbnBtn.setOnClickListener {
                        startActivity(Intent(requireContext(), AuthActivity::class.java))
                    }
                    if (it.image) view.imageEmptyState.setImageResource(+R.raw.empty) else view.imageEmptyState.setImageResource(
                        +R.raw.signup_pic
                    )
                }
            } else
                emptySateLayout?.visibility = View.GONE
        }

        addToCartBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ShippingActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, viewModel.purchaseDetailLiveData.value)
            })
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onClickProductImage(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetails::class.java).apply {
            putExtra(EXTRA_KEY_DATA, cartItem.product)
        })
    }

    override fun increaseCountProduct(cartItem: CartItem) {
        viewModel.increaseCartCount(cartItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartAdapter?.increaseCountItem(cartItem)
                }
            })
    }

    override fun decreaseCountProduct(cartItem: CartItem) {
        viewModel.decreaseCartCount(cartItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartAdapter?.decreaseCountItem(cartItem)
                }
            })
    }

    override fun onRemoveProductFromCart(cartItem: CartItem) {
        viewModel.removeItem(cartItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartAdapter?.removeItem(cartItem)
                }
            })
    }
}