package com.example.digishoes.data.repo.shipping

import com.example.digishoes.data.Checkout
import com.example.digishoes.data.OrderHistoryItem
import com.example.digishoes.data.SubmitOrderResult
import com.example.digishoes.service.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(private val apiService: ApiService) : OrderDataSource {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name", firstName)
            addProperty("last_name", lastName)
            addProperty("postal_code", postalCode)
            addProperty("mobile", phoneNumber)
            addProperty("address", address)
            addProperty("payment_method", paymentMethod)
        })
    }

    override fun checkout(order_id: Int): Single<Checkout> {
        return apiService.checkout(order_id)
    }

    override fun list(): Single<List<OrderHistoryItem>> = apiService.list()
}