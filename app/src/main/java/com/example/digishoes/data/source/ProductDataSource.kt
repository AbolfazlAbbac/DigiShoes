package com.example.digishoes.data.source

import com.example.digishoes.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    fun getProduct(): Single<List<Product>>

    fun getFavorite(): Single<List<Product>>

    fun addFavorite(): Completable

    fun deleteFavorite(): Completable
}