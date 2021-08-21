package com.example.digishoes.data.repo

import com.example.digishoes.data.*
import com.example.digishoes.data.source.CartDataSource
import io.reactivex.Single

class CartRepositoryImp(val cartRemoteDataSource: CartDataSource) : CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        cartRemoteDataSource.addToCart(productId)


    override fun removeCart(cartItemId: Int): Single<MessageResponse> =
        cartRemoteDataSource.removeCart(cartItemId)

    override fun get(): Single<CartResponse> = cartRemoteDataSource.get()

    override fun changeItemCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        cartRemoteDataSource.changeItemCount(cartItemId, count)

    override fun getItemCount(): Single<CartItemCount> = cartRemoteDataSource.getItemCount()

}