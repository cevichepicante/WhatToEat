package com.cevichepicante.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cevichepicante.ui.util.ComponentUtil.asDp

@Composable
fun CommonToolBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ToolBarBackButton(
                onClick = onBack
            )
            Text(
                text = title,
                fontSize = 20.asDp(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 45.dp)
            )
        }
    }
}

@Composable
fun ToolBarBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "<",
        fontSize = 25.asDp(),
        textAlign = TextAlign.Center,
        modifier = modifier
            .width(25.dp)
            .clickable {
                onClick()
            }
    )
}

@Preview
@Composable
fun CommonToolBarPreview() {
    CommonToolBar(
        title = "주문",
        onBack = {},
    )
}