package com.example.digishoes.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.common.NikeView
import com.example.digishoes.data.Comment
import com.example.digishoes.data.Product
import com.example.digishoes.data.repo.CommentRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(bundle: Bundle, commentRepository: CommentRepository) :
    NikeView.DigiViewModel() {
    val productLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        commentRepository.getAll(productLiveData.value!!.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DigiSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value = t
                }

            })
    }
}