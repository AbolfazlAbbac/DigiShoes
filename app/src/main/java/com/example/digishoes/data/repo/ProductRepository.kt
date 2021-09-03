package com.example.digishoes.data.repo

import com.example.digishoes.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProduct(sort: Int): Single<List<Product>>

    fun getFavorite(): Single<List<Product>>

    fun addFavorite(product: Product): Completable

    fun deleteFavorite(product: Product): Completable
}