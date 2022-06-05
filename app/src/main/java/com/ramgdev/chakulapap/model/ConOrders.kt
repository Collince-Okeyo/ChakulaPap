package com.ramgdev.chakulapap.model

data class ConOrders(
    val id: Int,
    val customerName: String = "",
    val foodName: String = "",
    val orderPrice: String = "",
    val numberOfOrders: String = "",
    val tableNumber: String = ""
)

