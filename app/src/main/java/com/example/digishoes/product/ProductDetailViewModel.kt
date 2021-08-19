package com.example.digishoes.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.*
import com.example.digishoes.data.Comment
import com.example.digishoes.data.Product
import com.example.digishoes.data.repo.CartRepository
import com.example.digishoes.data.repo.CommentRepository
import io.reactivex.Completable

class ProductDetailViewModel(
    bundle: Bundle,
    private val commentRepository: CommentRepository,
    private val addCartRepository: CartRepository
) :
    DigiViewModel() {
    val productLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBar.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetwork()
            .doFinally { progressBar.value = false }
            .subscribe(object : DigiSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value = t
                }

            })
    }

    fun onClickAddToCartBtn(): Completable =
        addCartRepository.addToCart(productLiveData.value!!.id).ignoreElement()

}