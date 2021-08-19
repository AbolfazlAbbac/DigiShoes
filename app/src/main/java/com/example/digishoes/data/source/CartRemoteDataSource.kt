package com.example.digishoes.data.source

import com.example.digishoes.data.AddToCartResponse
import com.example.digishoes.data.CartItem
import com.example.digishoes.data.CartItemCount
import com.example.digishoes.data.MessageResponse
import com.example.digishoes.service.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id", productId)
        }
    )

    override fun removeCart(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<CartItem> {
        TODO("Not yet implemented")
    }

    override fun changeItemCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}