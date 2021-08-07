package com.example.digishoes.service.http

import com.example.digishoes.data.Banner
import com.example.digishoes.data.Product
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("product/list")
    fun getProduct(): Single<List<Product>>

    @GET("banner/slider")
    fun getBanner(): Single<List<Banner>>
}

fun getApiServiceInstance(): ApiService {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .build()
    return retrofit.create(ApiService::class.java)
}