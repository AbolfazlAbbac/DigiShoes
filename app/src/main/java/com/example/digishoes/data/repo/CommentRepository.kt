package com.example.digishoes.data.repo

import com.example.digishoes.data.Comment
import io.reactivex.Single

interface CommentRepository {

    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}