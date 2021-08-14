package com.example.digishoes.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.R
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.DigiView
import com.example.digishoes.common.asyncNetwork
import com.example.digishoes.data.Product
import com.example.digishoes.data.repo.ProductRepository

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    DigiView.DigiViewModel() {
    val productLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitle = arrayOf(
        R.string.latest,
        R.string.sortPopular,
        R.string.sortPriceLowToHigh,
        R.string.sortPriceLowToHigh
    )

    init {
        getProduct()
        selectedSortTitleLiveData.value = sortTitle[sort]
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

    fun selectedSortChanged(sort: Int) {
        this.sort = sort
        this.selectedSortTitleLiveData.value = sortTitle[sort]
        getProduct()
    }
}