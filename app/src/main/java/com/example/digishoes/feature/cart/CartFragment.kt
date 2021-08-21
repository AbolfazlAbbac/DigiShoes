package com.example.digishoes.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.digishoes.R
import com.example.digishoes.common.NikeFragment
import org.koin.android.ext.android.inject
import timber.log.Timber

class CartFragment : NikeFragment() {
    val viewModel: CartViewModel by inject()

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
        }

        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner) {
            Timber.i("Purchase Detail -> $it")
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }
}