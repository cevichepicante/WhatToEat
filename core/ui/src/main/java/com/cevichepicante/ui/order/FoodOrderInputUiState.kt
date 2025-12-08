package com.cevichepicante.ui.order

sealed class FoodOrderInputUiState {

    data object None: FoodOrderInputUiState()

    data object Unmodified: FoodOrderInputUiState()

    data object Valid: FoodOrderInputUiState()

    data class MissingInput(
        val list: List<String>
    ): FoodOrderInputUiState()
}