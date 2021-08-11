package com.example.digishoes.data.source

import com.example.digishoes.data.Comment
import com.example.digishoes.service.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {
    override fun getAll(productId: Int): Single<List<Comment>> = apiService.getComments(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}