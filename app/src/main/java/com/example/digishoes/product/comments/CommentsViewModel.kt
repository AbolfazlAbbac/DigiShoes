package com.example.digishoes.product.comments

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.NikeView
import com.example.digishoes.common.asyncNetwork
import com.example.digishoes.data.Comment
import com.example.digishoes.data.repo.CommentRepository
import io.reactivex.SingleObserver

class CommentsViewModel(val productId: Int, val commentRepository: CommentRepository) :
    NikeView.DigiViewModel() {

    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        commentRepository.getAll(productId)
            .asyncNetwork()
            .subscribe(object : DigiSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }
}