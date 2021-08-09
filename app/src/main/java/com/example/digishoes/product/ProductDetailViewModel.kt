package com.example.digishoes.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.common.NikeView
import com.example.digishoes.data.Product

class ProductDetailViewModel(bundle: Bundle) : NikeView.DigiViewModel() {
    val productLiveData = MutableLiveData<Product>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }
}