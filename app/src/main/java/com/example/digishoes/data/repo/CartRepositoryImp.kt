package com.example.digishoes.data.repo

import com.example.digishoes.data.AddToCartResponse
import com.example.digishoes.data.CartItem
import com.example.digishoes.data.CartItemCount
import com.example.digishoes.data.MessageResponse
import com.example.digishoes.data.source.CartDataSource
import io.reactivex.Single

class CartRepositoryImp(val cartRemoteDataSource: CartDataSource) : CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        cartRemoteDataSource.addToCart(productId)


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