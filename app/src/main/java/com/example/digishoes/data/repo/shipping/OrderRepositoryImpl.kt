package com.example.digishoes.data.repo.shipping

import com.example.digishoes.data.Checkout
import com.example.digishoes.data.SubmitOrderResult
import io.reactivex.Single

class OrderRepositoryImpl(private val orderRemoteDataSource: OrderDataSource) : OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderRemoteDataSource.submit(
            firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod
        )
    }

    override fun checkout(order_id: Int): Single<Checkout> {
        return orderRemoteDataSource.checkout(order_id)
    }
}