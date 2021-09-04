package com.example.digishoes.data.repo.shipping

import com.example.digishoes.data.Checkout
import com.example.digishoes.data.OrderHistoryItem
import com.example.digishoes.data.SubmitOrderResult
import io.reactivex.Single

interface OrderDataSource {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>


    fun checkout(order_id: Int): Single<Checkout>
    fun list(): Single<List<OrderHistoryItem>>
}