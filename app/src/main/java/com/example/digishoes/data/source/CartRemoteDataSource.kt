package com.example.digishoes.data.source

import com.example.digishoes.data.*
import com.example.digishoes.data.repo.CartRepository
import com.example.digishoes.service.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id", productId)
        }
    )

    override fun removeCart(cartItemId: Int): Single<MessageResponse> =
        apiService.removeItemCart(JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
        })

    override fun get(): Single<CartResponse> = apiService.getCart()

    override fun changeItemCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        apiService.changeItemCount(JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
            addProperty("count", count)
        })

    override fun getItemCount(): Single<CartItemCount> = apiService.getItemsCartCount()
}