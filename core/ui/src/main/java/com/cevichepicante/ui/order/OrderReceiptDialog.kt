package com.cevichepicante.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderReceiptDialog(
    show: Boolean,
    receipt: FoodOrderReceipt,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    if(show) {
        BasicAlertDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color.White)
            ) {
                OrderReceiptField(
                    category = "주소",
                    content = receipt.address,
                )
                OrderReceiptField(
                    category = "이름",
                    content = receipt.clientName,
                )
                OrderReceiptField(
                    category = "배달원",
                    content = receipt.delivererNumber
                )
                HorizontalDivider(
                    thickness = 1.dp
                )
                Text(
                    text = receipt.foodName
                )
                Text(
                    text = "총 ${receipt.price} 원"
                )

                Button(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "홈으로"
                    )
                }
            }
        }
    }
}

@Composable
fun OrderReceiptField(
    category: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = category
        )
        Text(
            text = content,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}