package com.example.digishoes.data.source

import androidx.room.Dao
import com.example.digishoes.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    fun getProduct(sort: Int): Single<List<Product>>

    fun getFavorite(): Single<List<Product>>

    fun addFavorite(product: Product): Completable

    fun deleteFavorite(product: Product): Completable
}