package com.example.digishoes.data.repo

import com.example.digishoes.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProduct(sort: Int): Single<List<Product>>

    fun getFavorite(): Single<List<Product>>

    fun addFavorite(): Completable

    fun deleteFavorite(): Completable
}