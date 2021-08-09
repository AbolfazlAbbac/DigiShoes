package com.example.digishoes.data.repo

import com.example.digishoes.data.Product
import com.example.digishoes.data.source.ProductLocalDataSource
import com.example.digishoes.data.source.ProductRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImp(
    val productRemoteDataSource: ProductRemoteDataSource,
    val productLocalDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProduct(sort: Int): Single<List<Product>> =
        productRemoteDataSource.getProduct(sort)

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