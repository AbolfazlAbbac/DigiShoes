package com.example.digishoes.product.comments

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.DigiView
import com.example.digishoes.common.asyncNetwork
import com.example.digishoes.data.Comment
import com.example.digishoes.data.repo.CommentRepository

class CommentsViewModel(val productId: Int, val commentRepository: CommentRepository) :
    DigiView.DigiViewModel() {

    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBar.value = true

        commentRepository.getAll(productId)
            .asyncNetwork()
            .doFinally { progressBar.value = false }
            .subscribe(object : DigiSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }
}