package com.example.digishoes.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.common.DigiView
import com.example.digishoes.common.asyncNetwork
import com.example.digishoes.data.Comment
import com.example.digishoes.data.Product
import com.example.digishoes.data.repo.CommentRepository

class ProductDetailViewModel(bundle: Bundle, commentRepository: CommentRepository) :
    DigiView.DigiViewModel() {
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
}