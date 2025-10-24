package com.cevichepicante.foodpicker

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodType
import com.cevichepicante.ui.common.ComponentUtil.asDp

@Composable
fun PickingFoodScreen(
    onClickRecipe: (String) -> Unit,
    onClickOrder: (String) -> Unit,
    viewModel: PickingFoodViewModel,
    modifier: Modifier = Modifier
) {
    val foodList by viewModel.foodList.collectAsState(listOf())
    val cookingMaterialList by viewModel.cookingMaterialList.collectAsState(listOf())
    val cookingKindList by viewModel.cookingKindList.collectAsState(listOf())
    val cookingOccasionList by viewModel.cookingOccasionList.collectAsState(listOf())
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

    LaunchedEffect(foodList) {
        val occasionList = mutableListOf<String>()
        val materialList = mutableListOf<String>()
        val kindList = mutableListOf<String>()

        foodList.forEach { item ->
            item.type.let {
                val ocs = it.occasionCategory
                val mat = it.materialCategory
                val kind = it.kindCategory
                if(ocs.isNotEmpty() && !occasionList.contains(ocs)) {
                    occasionList.add(ocs)
                }
                if(mat.isNotEmpty() && !materialList.contains(mat)) {
                    materialList.add(mat)
                }
                if(kind.isNotEmpty() && !kindList.contains(kind)) {
                    kindList.add(kind)
                }
            }
        }
        Log.d("JSy", "food type state) ocs: $occasionList, mat: $materialList, kind: $kindList")
    }

    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        FoodFilters(
            materialList = cookingMaterialList,
            kindList = cookingKindList,
            occasionList = cookingOccasionList,
            onRequestFilter = {
                viewModel.setFoodFilter(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        FoodSlot(
            list = foodList,
            pagerState = pagerState,
            spin = spinSlot,
            enabledButton = pickedFood,
            onClickRecipe = {
                onClickRecipe(selectedFood?.id.orEmpty())
            },
            onClickOrder = {
                onClickOrder(selectedFood?.id.orEmpty())
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        SlotButtons(
            enabled = foodList.isNotEmpty(),
            onClickStart = {
                spinSlot = true
                pickedFood = false },
            onClickStop = {
                spinSlot = false
                pickedFood = true },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
private fun FoodFilters(
    materialList: List<String>,
    kindList: List<String>,
    occasionList: List<String>,
    onRequestFilter: (FoodType) -> Unit,
    modifier: Modifier = Modifier
) {
    var foodType by remember {  
        mutableStateOf(FoodType("", "", ""))
    }
    var occasionExpanded by remember {
        mutableStateOf(false)
    }
    var materialExpanded by remember {
        mutableStateOf(false)
    }
    var kindExpanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        FoodFilterDropDown(
            expanded = materialExpanded,
            category = "주재료",
            content = materialList,
            modifier = Modifier.weight(1f),
            onItemClick = {
                foodType = foodType.copy(materialCategory = it)
                materialExpanded = false
            },
            onExpandedStateRequest = { toExpanded ->
                materialExpanded = toExpanded
            }
        )
        FoodFilterDropDown(
            expanded = kindExpanded,
            category = "종류",
            content = kindList,
            modifier = Modifier.weight(1f),
            onItemClick = {
                foodType = foodType.copy(kindCategory = it)
                materialExpanded = false
            },
            onExpandedStateRequest = { toExpanded ->
                kindExpanded = toExpanded
            }
        )
        FoodFilterDropDown(
            expanded = occasionExpanded,
            category = "요리 상황",
            content = occasionList,
            modifier = Modifier.weight(1f),
            onItemClick = {
                foodType = foodType.copy(occasionCategory = it)
                materialExpanded = false
            },
            onExpandedStateRequest = { toExpanded ->
                occasionExpanded = toExpanded
            }
        )

        Button(
            modifier = Modifier.size(30.dp),
            onClick = {
                onRequestFilter(foodType)
            }
        ) { 
            Text(
                text = "설정"
            )
        }
    }
}

@Composable
private fun FoodFilterDropDown(
    expanded: Boolean,
    category: String,
    content: List<String>,
    onExpandedStateRequest: (Boolean) -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedContent by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = category,
        )
        Text(
            text = selectedContent.ifEmpty { "All" },
            modifier = Modifier
                .clickable {
                    onExpandedStateRequest(true)
                }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedStateRequest(false)
            },
            modifier = Modifier.height(200.dp),
            content = {
                FoodFilterDropDownItem(
                    content = "All",
                    onClick = {
                        selectedContent = ""
                        onItemClick("")
                    }
                )
                content.forEach { 
                    FoodFilterDropDownItem(
                        content = it,
                        onClick = {
                            selectedContent = it
                            onItemClick(it)
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun FoodFilterDropDownItem(
    content: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            FoodFilterDropDownText(
                text = content
            )
        },
        onClick = onClick
    )
}

@Composable
private fun FoodFilterDropDownText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
    )
}

@Composable
private fun FoodSlot(
    list: List<Food>,
    pagerState: PagerState,
    spin: Boolean,
    enabledButton: Boolean,
    onClickRecipe: () -> Unit,
    onClickOrder: () -> Unit,
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
            if(spin && list.isNotEmpty()) {
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

    Column(
        modifier = modifier
            .background(
                color = Color.Red.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            if(list.isEmpty()) {
                FoodSlotShutter(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            } else {
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
        FoodTreatButtons(
            onClickCook = onClickRecipe,
            onClickOrder = onClickOrder,
            enabled = enabledButton,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
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
        fontSize = 20.asDp(),
        modifier = modifier
            .background(Color.Gray)
    )
}

@Composable
private fun FoodSlotShutter(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = "?",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun SlotButtons(
    enabled: Boolean,
    onClickStart: () -> Unit,
    onClickStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        SlotButton(
            text = "Start",
            textColor = Color.Black,
            containerColor = Color.Gray,
            enabled = enabled,
            onClick = onClickStart,
            modifier = Modifier.weight(1f)
        )

        SlotButton(
            text = "Stop",
            textColor = Color.Black,
            containerColor = Color.Gray,
            enabled = enabled,
            onClick = onClickStop,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FoodTreatButtons(
    enabled: Boolean,
    onClickCook: () -> Unit,
    onClickOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        SlotButton(
            text = "Cook",
            textColor = Color.Black,
            containerColor = Color.Transparent,
            enabled = enabled,
            onClick = onClickCook,
            modifier = Modifier.weight(1f)
        )
        SlotButton(
            text = "Order",
            textColor = Color.Black,
            containerColor = Color.Transparent,
            enabled = enabled,
            onClick = onClickOrder,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SlotButton(
    text: String,
    textColor: Color,
    containerColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = enabled,
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
