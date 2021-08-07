package com.example.digishoes.data.source

import com.example.digishoes.data.Product
import com.example.digishoes.service.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {
    override fun getProduct(): Single<List<Product>> = apiService.getProduct()

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