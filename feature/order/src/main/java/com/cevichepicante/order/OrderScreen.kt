package com.cevichepicante.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodOrderReq
import com.cevichepicante.ui.common.OrderReceiptDialog
import com.cevichepicante.ui.util.ComponentUtil.asDp
import com.cevichepicante.ui.order.FoodOrderUiState
import com.cevichepicante.ui.value.SlotFrame

@Composable
fun OrderScreen(
    foodId: String,
    viewModel: OrderViewModel,
    onFinishOrder: () ->  Unit,
    modifier: Modifier = Modifier
) {
    val orderFood by viewModel.orderFood.collectAsState()
    var orderInfo by remember {
        mutableStateOf(
            FoodOrderReq(foodId, "", "", "", 27000)
        )

    }
    val orderUiState by viewModel.orderUiState.collectAsState()
    var showReceiptDialog by remember {
        mutableStateOf(false)
    }

    if(showReceiptDialog) {
        val state = orderUiState as? FoodOrderUiState.Success
        if(state != null) {
            OrderReceiptDialog(
                show = true,
                receipt = state.orderReceipt,
                onDismissRequest = {
                    showReceiptDialog = false
                    onFinishOrder() },
                modifier = Modifier.padding(30.dp)
                    .fillMaxSize()
            )
        }
    }

    LaunchedEffect(foodId) {
        viewModel.getOrderFoodInfo(foodId)
    }

    LaunchedEffect(orderUiState) {
        when(val state = orderUiState) {
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

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(SlotFrame)
                .height(55.dp)
                .padding(horizontal = 20.dp)
                .wrapContentHeight(Alignment.CenterVertically)
        ) {
            Text(
                text = "back",
                color = Color.Gray,
                fontSize = 14.asDp(),
                modifier = Modifier.wrapContentSize()
                    .clickable {
                        // TODO: quit alert dialog ..
                        onFinishOrder()
                    }
            )
        }
        OrderClientInfo(
            name = orderInfo.clientName,
            number = orderInfo.clientNumber,
            address = orderInfo.address,
            onNameValueChanged = { orderInfo = orderInfo.copy(clientName = it) },
            onNumberValueChanged = { orderInfo = orderInfo.copy(clientNumber = it) },
            onAddressValueChanged = { orderInfo = orderInfo.copy(address = it) }
        )

        OrderFoodInfo(
            food = orderFood,
            count = 2 //todo
        )

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    viewModel.requestOrder(orderInfo)
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
    OrderFormBox {
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
    count: Int,
) {
    val foodPrice = 12000
    OrderFormBox {
        Text(
            text = food?.name.orEmpty(),
        )
        HorizontalDivider(
            thickness = 1.dp
        )
        Text(
            text = foodPrice.toString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Text(
            text = "x $count",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Text(
            text = "총 ${foodPrice.times(count)} 원"
        )
    }
}
@Composable
private fun OrderFormBox(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.Gray
            )
            .padding(horizontal = 20.dp, vertical = 10.dp)
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
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.White)
                        .padding(4.dp)
                ) {
                    inner()
                }
            }
        )
    }
}