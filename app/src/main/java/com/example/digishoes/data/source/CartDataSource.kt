package com.example.digishoes.data.source

import com.example.digishoes.data.AddToCartResponse
import com.example.digishoes.data.CartItem
import com.example.digishoes.data.CartItemCount
import com.example.digishoes.data.MessageResponse
import io.reactivex.Single

interface CartDataSource {

    fun addToCart(productId: Int): Single<AddToCartResponse>

    fun removeCart(cartItemId: Int): Single<MessageResponse>

    fun get(): Single<CartItem>

    fun changeItemCount(cartItemId: Int, count: Int): Single<AddToCartResponse>

    fun getItemCount(): Single<CartItemCount>
}