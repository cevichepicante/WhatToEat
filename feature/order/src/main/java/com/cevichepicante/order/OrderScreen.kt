package com.cevichepicante.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderScreen(
    foodId: String,
    viewModel: OrderViewModel,
    onFinishOrder: () ->  Unit,
    modifier: Modifier = Modifier
) {

    Column {
        OrderTextField(
            title = "이름",
            onValueChanged = {

            }
        )
    }
}

@Composable
fun OrderTextField(
    title: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        TextField(
            value = currentText,
            onValueChange = {
                currentText = it
                onValueChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
    }
}