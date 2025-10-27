package com.cevichepicante.model

data class FoodOrderReq(
    val foodId: String,
    val address: String,
    val clientName: String,
    val clientNumber: String,
    val price: Int
) {
}

data class FoodOrderRes(
    val orderId: String,
    val foodId: String,
    val address: String,
    val clientName: String,
    val price: Int,
    val leadTime: Int,
    val delivererNumber: String,
)