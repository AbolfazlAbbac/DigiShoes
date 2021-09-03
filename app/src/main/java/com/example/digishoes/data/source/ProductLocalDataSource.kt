package com.example.digishoes.data.source

import androidx.room.*
import com.example.digishoes.data.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource : ProductDataSource {
    override fun getProduct(sort: Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavorite(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addFavorite(product: Product): Completable

    @Delete
    override fun deleteFavorite(product: Product): Completable
}