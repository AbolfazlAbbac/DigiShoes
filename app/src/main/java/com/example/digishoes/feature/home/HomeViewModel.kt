package com.example.digishoes.feature.home

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.data.repo.ProductRepository
import com.example.digishoes.common.*
import com.example.digishoes.data.*
import com.example.digishoes.data.repo.BannerRepository
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val productRepository: ProductRepository, bannerRepository: BannerRepository) :
    DigiViewModel() {
    val productLiveData = MutableLiveData<List<Product>>()
    val productLiveData_Popular = MutableLiveData<List<Product>>()
    val bannerLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBar.value = true
        productRepository.getProduct(LATEST_SORT)
            .asyncNetwork()
            .doFinally { progressBar.value = false }
            .subscribe(object : DigiSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }

            })
        productRepository.getProduct(LATEST_POPULAR)
            .asyncNetwork()
            .doFinally { progressBar.value = false }
            .subscribe(object : DigiSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData_Popular.value = t
                }

            })

        bannerRepository.getBanners()
            .asyncNetwork()
            .subscribe(object : DigiSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }

            })


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