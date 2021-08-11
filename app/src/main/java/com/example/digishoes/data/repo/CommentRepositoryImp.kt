package com.example.digishoes.data.repo

import com.example.digishoes.data.Comment
import com.example.digishoes.data.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImp(val commentDataSource: CommentDataSource) : CommentRepository {
    override fun getAll(productId: Int): Single<List<Comment>> =
        commentDataSource.getAll(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}