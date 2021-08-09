package com.example.digishoes.feature.main

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.data.repo.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.example.digishoes.common.*
import com.example.digishoes.data.*
import com.example.digishoes.data.repo.BannerRepository

class MainViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) :
    NikeView.DigiViewModel() {
    val productLiveData = MutableLiveData<List<Product>>()
    val productLiveData_Popular = MutableLiveData<List<Product>>()
    val bannerLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBar.value = true
        productRepository.getProduct(LATEST_SORT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBar.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }

            })
        productRepository.getProduct(LATEST_POPULAR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBar.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData_Popular.value = t
                }

            })

        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }

            })
    }
}