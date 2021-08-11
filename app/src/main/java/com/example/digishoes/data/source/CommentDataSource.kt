package com.example.digishoes.data.source

import com.example.digishoes.data.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}