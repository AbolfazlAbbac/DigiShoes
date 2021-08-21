package com.example.digishoes.data.repo

import com.example.digishoes.data.*
import io.reactivex.Single

interface CartRepository {

    fun addToCart(productId: Int): Single<AddToCartResponse>

    fun removeCart(cartItemId: Int): Single<MessageResponse>

    fun get(): Single<CartResponse>

    fun changeItemCount(cartItemId: Int, count: Int): Single<AddToCartResponse>

    fun getItemCount(): Single<CartItemCount>
}