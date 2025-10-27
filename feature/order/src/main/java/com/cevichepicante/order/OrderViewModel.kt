package com.cevichepicante.order

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevichepicante.common.FormatUtil
import com.cevichepicante.data.repository.FoodOrderRepository
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodOrderReq
import com.cevichepicante.model.onFailure
import com.cevichepicante.model.onSuccess
import com.cevichepicante.ui.order.FoodOrderReceipt
import com.cevichepicante.ui.order.FoodOrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: FoodOrderRepository
): ViewModel() {

    private val _orderFood = MutableStateFlow<Food?>(null)
    val orderFood: StateFlow<Food?>
        get() = _orderFood.asStateFlow()

    private val _orderUiState = MutableStateFlow<FoodOrderUiState>(FoodOrderUiState.None)
    val orderUiState get() = _orderUiState.asStateFlow()

    fun getOrderFoodInfo(foodId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchOrderFoodInfo(foodId)?.let {
                _orderFood.emit(it)
            }
        }
    }

    fun requestOrder(orderInfo: FoodOrderReq) {
        viewModelScope.launch {
            repo.requestOrder(orderInfo)
                .onSuccess { data ->
                    _orderUiState.emit(
                        FoodOrderUiState.Success(
                            orderReceipt = FoodOrderReceipt(
                                orderId = data.orderId,
                                foodName = data.foodId,
                                clientName = data.clientName,
                                address = data.address,
                                delivererNumber = data.delivererNumber,
                                price = data.price.toString(),
                                leadTime = FormatUtil.getDurationTimeString(data.leadTime)
                            )
                        )
                    )
                }
                .onFailure { code, msg ->
                    _orderUiState.emit(
                        FoodOrderUiState.Failure(
                            errorCode = code
                        )
                    )
                }
        }
    }
}