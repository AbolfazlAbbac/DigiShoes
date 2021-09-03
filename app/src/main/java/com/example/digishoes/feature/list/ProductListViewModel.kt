package com.example.digishoes.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.R
import com.example.digishoes.common.*
import com.example.digishoes.data.Product
import com.example.digishoes.data.repo.ProductRepository
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    DigiViewModel() {
    val productLiveData = MutableLiveData<List<Product>>()
    val selectedSortLiveData = MutableLiveData<Int>()
    val sorTitle = arrayOf(
        R.string.sortLatestProduct,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )

    init {
        getProduct()
        selectedSortLiveData.value = sorTitle[sort]
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

    fun selectChangeSort(sort: Int) {
        this.sort = sort
        this.selectedSortLiveData.value = sorTitle[sort]
        getProduct()
    }

    fun addProductFavorite(product: Product) {
        if (product.isFavorite) {
            productRepository.deleteFavorite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        } else {
            productRepository.addFavorite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = true

                    }
                })
        }
    }
}