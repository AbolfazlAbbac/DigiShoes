package com.example.digishoes.feature.favorite

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.DigiCompletableObserver
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.DigiViewModel
import com.example.digishoes.data.EmptyState
import com.example.digishoes.data.Product
import com.example.digishoes.data.repo.ProductRepository
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteViewModel(private val productRepository: ProductRepository) : DigiViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()
    val emptyState = MutableLiveData<EmptyState>()

    init {
        productRepository.getFavorite()
            .subscribeOn(Schedulers.io())
            .doAfterSuccess {
                emptyState.value = EmptyState(false)
            }
            .subscribe(object : DigiSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.postValue(t)
                }
            })
    }

    fun removeFavorite(product: Product) {
        return productRepository.deleteFavorite(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                }
            })
    }


}