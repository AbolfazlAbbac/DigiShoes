package com.example.digishoes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)

@Parcelize
data class PurchaseDetail(var payable_price: Int, var shipping_cost: Int, var total_price: Int) :
    Parcelable