package com.example.digishoes.data

data class CartItem(
    val cart_item_id: Int,
    var count: Int,
    val product: Product,
    var changeProgressBarCartIsVisibility:Boolean = false
)