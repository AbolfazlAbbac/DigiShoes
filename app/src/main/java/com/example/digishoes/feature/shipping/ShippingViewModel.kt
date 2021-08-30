package com.example.digishoes.feature.shipping

import com.example.digishoes.common.DigiViewModel
import com.example.digishoes.data.Checkout
import com.example.digishoes.data.SubmitOrderResult
import com.example.digishoes.data.repo.shipping.OrderRepository
import io.reactivex.Single

const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"

class ShippingViewModel(private val orderRepository: OrderRepository) : DigiViewModel() {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderRepository.submit(
            firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod
        )
    }

    fun checkout(order_id: Int): Single<Checkout> {
        return orderRepository.checkout(order_id)
    }
}