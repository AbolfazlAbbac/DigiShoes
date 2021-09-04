package com.example.digishoes.data

data class OrderHistoryItem(
    val id: Int,
    val order_items: List<OrderItem>,
    val payable: Int
)