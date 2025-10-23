package com.cevichepicante.foodpicker

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cevichepicante.model.Food

@Composable
fun PickingFoodScreen(
    onClickRecipe: (String) -> Unit,
    onClickOrder: (String) -> Unit,
    viewModel: PickingFoodViewModel,
    modifier: Modifier = Modifier
) {
    val foodList by viewModel.foodList.collectAsState(listOf())
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            Int.MAX_VALUE
        }
    )
    var spinSlot by rememberSaveable {
        mutableStateOf(true)
    }
    var pickedFood by rememberSaveable {
        mutableStateOf(false)
    }
    val selectedFood by remember(
        keys = arrayOf(foodList, pagerState, spinSlot, pickedFood)
    ) {
        derivedStateOf {
            if(pickedFood) {
                val actualIndex = pagerState.currentPage.let {
                    if(it > 0) {
                        it % foodList.size
                    } else {
                        0
                    }
                }
                foodList.getOrNull(actualIndex)
            } else {
                null
            }
        }
    }

    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        if(foodList.isNotEmpty()) {
            FoodSlot(
                list = foodList,
                pagerState = pagerState,
                spin = spinSlot,
                modifier = Modifier.fillMaxWidth()
            )
            FoodTreatButtons(
                onClickCook = {
                    onClickRecipe(selectedFood?.id.orEmpty())
                },
                onClickOrder = {
                    onClickOrder(selectedFood?.id.orEmpty())
                },
                visible = pickedFood,
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
            )
            SlotButtons(
                onClickStart = {
                    spinSlot = true
                    pickedFood = false },
                onClickStop = {
                    spinSlot = false
                    pickedFood = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            )
        }

    }
}

@Composable
private fun FoodSlot(
    list: List<Food>,
    pagerState: PagerState,
    spin: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollAnimation = tween<Float>(
        durationMillis = 80,
        easing = LinearEasing
    )
    LaunchedEffect(
        key1 = pagerState,
        key2 = list,
        key3 = spin
    ) {
        snapshotFlow {
            pagerState.currentPage
        }.collect {
            if(spin) {
                val nextPage = it.inc()
                if(nextPage in 0 .. Int.MAX_VALUE) {
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = scrollAnimation
                    )
                }
            } else {
                pagerState.scrollToPage(it)
            }
        }
    }

    Box(
        modifier = modifier
            .background(
                color = Color.Red,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            VerticalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                val actualIndex = page % list.size
                val item = list.getOrNull(actualIndex)
                val foodName = item?.name.orEmpty()

                if(foodName.isNotEmpty()) {
                    FoodSlotItem(
                        content = foodName,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun FoodSlotItem(
    content: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = content,
        textAlign = TextAlign.Center,
        modifier = modifier
            .background(Color.Gray)
    )
}

@Composable
private fun SlotButtons(
    onClickStart: () -> Unit,
    onClickStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        SlotButton(
            text = "Start",
            textColor = Color.Black,
            containerColor = Color.Gray,
            onClick = onClickStart,
            modifier = Modifier.weight(1f)
        )

        SlotButton(
            text = "Stop",
            textColor = Color.Black,
            containerColor = Color.Gray,
            onClick = onClickStop,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FoodTreatButtons(
    onClickCook: () -> Unit,
    onClickOrder: () -> Unit,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    if(visible) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            SlotButton(
                text = "Cook",
                textColor = Color.Black,
                containerColor = Color.Transparent,
                onClick = onClickCook,
                modifier = Modifier.weight(1f)
            )
            SlotButton(
                text = "Order",
                textColor = Color.Black,
                containerColor = Color.Transparent,
                onClick = onClickOrder,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SlotButton(
    text: String,
    textColor: Color,
    containerColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 8.dp)
            .background(
                color = containerColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}
