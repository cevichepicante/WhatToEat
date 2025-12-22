package com.cevichepicante.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodOrderReq
import com.cevichepicante.ui.order.OrderReceiptDialog
import com.cevichepicante.ui.order.FoodOrderInputUiState
import com.cevichepicante.ui.util.ComponentUtil.asDp
import com.cevichepicante.ui.order.FoodOrderUiState
import com.cevichepicante.ui.util.ComponentUtil.bottomBorder
import com.cevichepicante.ui.value.SlotFrame

@Composable
fun OrderScreen(
    foodId: String,
    viewModel: OrderViewModel,
    onFinishOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    val orderFood by viewModel.orderFood.collectAsState()
    val inputValidState by viewModel.foodOrderInputUiState.collectAsState(FoodOrderInputUiState.None)
    val orderUiState by viewModel.orderUiState.collectAsState()

    var orderInfo by remember { mutableStateOf(FoodOrderReq(foodId = foodId)) }
    var clientName by remember { mutableStateOf("") }
    var clientAddress by remember { mutableStateOf("") }
    var clientNumber by remember { mutableStateOf("") }
    var foodAmount by remember(foodId) { mutableIntStateOf(1) }

    var showReceiptDialog by remember { mutableStateOf(false) }
    val onClickChangeAmount by rememberUpdatedState<(Boolean) -> Unit> { toIncrease ->
        val newAmount = if (toIncrease) {
            foodAmount.inc()
        } else {
            foodAmount.dec()
        }.coerceAtLeast(0)

        if (foodAmount != newAmount) {
            foodAmount = newAmount
        }
    }

    if (showReceiptDialog) {
        val state = orderUiState as? FoodOrderUiState.Success
        if (state != null) {
            OrderReceiptDialog(
                show = true,
                receipt = state.orderReceipt,
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxSize(),
                onDismissRequest = {
                    onFinishOrder()
                    showReceiptDialog = false
                }
            )
        }
    }

    LaunchedEffect(foodId) {
        viewModel.getOrderFoodInfo(foodId)
    }

    LaunchedEffect(
        keys = arrayOf<Any>(clientName, clientNumber, clientAddress, foodAmount)
    ) {
        orderInfo = orderInfo.copy(
            clientName = orderInfo.clientName.updated(clientName),
            clientNumber = orderInfo.clientNumber.updated(clientNumber),
            address = orderInfo.address.updated(clientAddress),
            foodAmount = foodAmount,
            foodPrice = foodAmount.times(orderFood?.price?: 0)
        )
    }

    LaunchedEffect(orderUiState) {
        when (val state = orderUiState) {
            is FoodOrderUiState.Processing -> {

            }

            is FoodOrderUiState.Success -> {
                showReceiptDialog = true
            }

            is FoodOrderUiState.Failure -> {
                // TODO by error code
            }

            else -> {}
        }
    }

    LaunchedEffect(inputValidState) {
        when (inputValidState) {
            is FoodOrderInputUiState.Valid -> {
                viewModel.requestOrder(orderInfo)
            }

            is FoodOrderInputUiState.MissingInput -> {

            }

            is FoodOrderInputUiState.Unmodified -> {

            }

            else -> {}
        }
    }

    Column(
        modifier = modifier
    ) {
        // 주문 고객 정보
        OrderClientInfo(
            name = clientName,
            number = clientNumber,
            address = clientAddress,
            onNameValueChanged = { clientName = it },
            onNumberValueChanged = { clientNumber = it },
            onAddressValueChanged = { clientAddress = it }
        )

        // 주문 음식 정보
        OrderFoodInfo(
            food = orderFood,
            amount = foodAmount,
            onClickChangeAmount = onClickChangeAmount
        )

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    viewModel.submitInfoValidation(orderInfo)
                }
            ) {
                Text(
                    text = "주문"
                )
            }
        }
    }
}

@Composable
private fun OrderClientInfo(
    name: String,
    number: String,
    address: String,
    onNameValueChanged: (String) -> Unit,
    onNumberValueChanged: (String) -> Unit,
    onAddressValueChanged: (String) -> Unit,
) {
    OrderFormBox(
        itemSpace = 8.dp
    ) {
        OrderTextField(
            title = "이름",
            value = name,
            onValueChanged = {
                onNameValueChanged(it)
            }
        )
        OrderTextField(
            title = "전화번호",
            value = number,
            onValueChanged = {
                onNumberValueChanged(it)
            }
        )
        OrderTextField(
            title = "주소",
            value = address,
            onValueChanged = {
                onAddressValueChanged(it)
            }
        )
    }
}

@Composable
private fun OrderFoodInfo(
    food: Food?,
    amount: Int,
    onClickChangeAmount: (Boolean) -> Unit
) {
    val price = food?.price?: 0

    OrderFormBox {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = food?.name.orEmpty(),
            )
            Text(
                text = price.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        FoodAmountController(
            currentValue = amount,
            onClickChangeAmount = onClickChangeAmount
        )
        Text(
            text = "총 ${price.times(amount)} 원",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun FoodAmountController(
    currentValue: Int,
    onClickChangeAmount: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        FoodAmountControlButton(
            content = "+",
            onClick = {
                onClickChangeAmount(true)
            }
        )
        Text(
            text = currentValue.toString(),
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        FoodAmountControlButton(
            content = "-",
            onClick = {
                onClickChangeAmount(false)
            }
        )
    }
}

@Composable
private fun FoodAmountControlButton(
    content: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = content,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            fontSize = 20.asDp()
        )
    }
}

@Composable
private fun OrderFormBox(
    itemSpace: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(itemSpace),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.Gray
            )
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        content()
    }
}

@Composable
fun OrderTextField(
    title: String,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = {
                onValueChanged(it)
            },
            decorationBox = { inner ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(4.dp)
                        .bottomBorder(
                            color = Color.Gray,
                            width = 1.dp
                        )
                ) {
                    inner()
                }
            }
        )
    }
}