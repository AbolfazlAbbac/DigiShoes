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
    override fun getProduct(sort: Int): Single<List<Product>> {
        return productLocalDataSource.getFavorite().flatMap { favoriteProduct ->
            productRemoteDataSource.getProduct(sort)
                .doOnSuccess { product ->
                    val productFavoriteIds = favoriteProduct.map {
                        it.id
                    }
                    product.forEach {
                        if (productFavoriteIds.contains(it.id))
                            it.isFavorite = true
                    }
                }
        }
    }

    override fun getFavorite(): Single<List<Product>> {
        return productLocalDataSource.getFavorite()
    }

    override fun addFavorite(product: Product): Completable {
        return productLocalDataSource.addFavorite(product)
    }

    override fun deleteFavorite(product: Product): Completable {
        return productLocalDataSource.deleteFavorite(product)
    }

}