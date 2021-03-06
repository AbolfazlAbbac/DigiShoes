package com.example.digishoes.service.http

import com.example.digishoes.data.*
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET("product/list")
    fun getProduct(@Query("sort") sort: String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanner(): Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id") productId: Int): Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject): Single<AddToCartResponse>

    @POST("cart/remove")
    fun removeItemCart(@Body jsonObject: JsonObject): Single<MessageResponse>

    @GET("cart/list")
    fun getCart(): Single<CartResponse>

    @POST("cart/changeCount")
    fun changeItemCount(@Body jsonObject: JsonObject): Single<AddToCartResponse>

    @GET("cart/count")
    fun getItemsCartCount(): Single<CartItemCount>

    @POST("auth/token")

    fun login(@Body jsonObject: JsonObject): Single<TokenResponse>

    @POST("user/register")
    fun signup(@Body jsonObject: JsonObject): Single<MessageResponse>

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>

    @POST("order/submit")
    fun submitOrder(@Body jsonObject: JsonObject): Single<SubmitOrderResult>

    @GET("order/checkout")
    fun checkout(@Query("order_id") order_id: Int): Single<Checkout>

    @GET("order/list")
    fun list(): Single<List<OrderHistoryItem>>
}

fun getApiServiceInstance(): ApiService {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequest = oldRequest.newBuilder()

            if (TokenContainer.token != null)
                newRequest.addHeader("Authorization", "Bearer ${TokenContainer.token}")

            newRequest.addHeader("Accept", "application/json")
            newRequest.method(oldRequest.method, oldRequest.body)
            return@addInterceptor it.proceed(newRequest.build())
        }.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .client(okHttpClient)
        .build()
    return retrofit.create(ApiService::class.java)
}