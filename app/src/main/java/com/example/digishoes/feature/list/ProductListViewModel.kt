package com.example.digishoes.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.DigiView
import com.example.digishoes.common.asyncNetwork
import com.example.digishoes.data.Product
import com.example.digishoes.data.repo.ProductRepository

class ProductListViewModel(val sort: Int, val productRepository: ProductRepository) :
    DigiView.DigiViewModel() {
    val productLiveData = MutableLiveData<List<Product>>()

    init {
        getProduct()
    }

    fun getProduct() {
        progressBar.value = true
        productRepository.getProduct(sort)
            .asyncNetwork()
            .doFinally { progressBar.value = false }
            .subscribe(object : DigiSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }
            })
    }
}