package com.example.digishoes.data.source

import com.example.digishoes.data.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductLocalDataSource:ProductDataSource {
    override fun getProduct(sort: Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavorite(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addFavorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFavorite(): Completable {
        TODO("Not yet implemented")
    }
}