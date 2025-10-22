package com.cevichepicante.ui.order

data class FoodOrderReceipt(
    val foodName: String,
    val clientName: String,
    val address: String,
    val delivererNumber: String,
    val price: String,
    val leadTime: String,
)

sealed class FoodOrderUiState {

    data object None: FoodOrderUiState()

    data object Processing: FoodOrderUiState()

    data class Success(
        val orderReceipt: FoodOrderReceipt
    ): FoodOrderUiState()

    data class Failure(
        val errorCode: String
    ): FoodOrderUiState()
}