package com.cevichepicante.foodpicker

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cevichepicante.composescrollshadow.HidingShadowPosition
import com.cevichepicante.composescrollshadow.ShadowIndicatedScaffold
import com.cevichepicante.composescrollshadow.ShadowIndicatedScrollScaffold
import com.cevichepicante.composescrollshadow.data.ShadowSettings
import com.cevichepicante.composescrollshadow.data.ShadowSideDirection
import com.cevichepicante.composescrollshadow.data.ShadowSideType
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodType
import com.cevichepicante.ui.util.ComponentUtil
import com.cevichepicante.ui.util.ComponentUtil.asDp
import com.cevichepicante.ui.util.StringUtil
import com.cevichepicante.ui.value.ShadowColor
import com.cevichepicante.ui.value.SlotDrumContainer
import com.cevichepicante.ui.value.SlotDrumContent
import com.cevichepicante.ui.value.SlotFrame

@Composable
fun PickingFoodScreen(
    onClickRecipe: (String) -> Unit,
    onClickOrder: (String) -> Unit,
    viewModel: PickingFoodViewModel,
    modifier: Modifier = Modifier
) {
    val foodList by viewModel.foodList.collectAsState(listOf())
    val foodFilter by viewModel.foodFilter.collectAsState()
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
        keys = arrayOf(foodList, pagerState, pickedFood)
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
            savedFilter = foodFilter,
            materialList = cookingMaterialList,
            kindList = cookingKindList,
            occasionList = cookingOccasionList,
            onRequestFilter = {
                viewModel.setFoodFilter(it)
                spinSlot = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        )
        FoodSlot(
            list = foodList,
            pagerState = pagerState,
            foodFilter = foodFilter,
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
                .height(100.dp)
        )
    }
}

@Composable
private fun FoodFilters(
    savedFilter: FoodType,
    materialList: List<String>,
    kindList: List<String>,
    occasionList: List<String>,
    onRequestFilter: (FoodType) -> Unit,
    modifier: Modifier = Modifier
) {
    var filter by remember(savedFilter) {
        mutableStateOf(savedFilter)
    }
    val isValueChanged by remember(
        key1 = savedFilter,
        key2 = filter
    ) {
        mutableStateOf(filter != savedFilter)
    }

    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FoodFilterMenu(
                category = "주재료",
                content = materialList,
                selected = filter.materialCategory,
                onItemClick = {
                    filter = filter.copy(materialCategory = it)
                }
            )
            FoodFilterMenu(
                category = "종류",
                content = kindList,
                selected = filter.kindCategory,
                onItemClick = {
                    filter = filter.copy(kindCategory = it)
                }
            )
            FoodFilterMenu(
                category = "요리 상황",
                content = occasionList,
                selected = filter.occasionCategory,
                onItemClick = {
                    filter = filter.copy(occasionCategory = it)
                }
            )
            FoodFilterButtons(
                isFilterValueChanged = isValueChanged,
                onRequestFilter = { onRequestFilter(filter) },
                onResetFilter = { filter = savedFilter }
            )
        }
    }
}

@Composable
private fun FoodFilterButtons(
    isFilterValueChanged: Boolean,
    onRequestFilter: () -> Unit,
    onResetFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Button(
            enabled = isFilterValueChanged,
            contentPadding = PaddingValues(1.dp),
            colors = ComponentUtil.getSlotFilterSetButtonColors(),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .wrapContentWidth(),
            onClick = onRequestFilter
        ) {
            Text(
                text = "적용",
            )
        }

        if(isFilterValueChanged) {
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = onResetFilter,
            ) {
                Text(
                    text = "초기화"
                )
            }
        }
    }
}

@Composable
private fun FoodFilterMenu(
    category: String,
    content: List<String>,
    selected: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val list by remember(content) {
        mutableStateOf(
            content.toMutableList().also {
                it.add(0, "전체")
            }.toList()
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShadowIndicatedScrollScaffold(
            hidingShadowIndex = HidingShadowPosition.FIRST,
            listState = listState,
            shadowSettings = ShadowSettings(
                shape = RoundedCornerShape(8.dp),
                color = ShadowColor,
                blurDp = 10.dp,
                sideType = ShadowSideType.SingleSide(
                    direction = ShadowSideDirection.Right,
                    drawInner = false
                )
            )
        ) {
            Text(
                text = category,
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    ).padding(horizontal = 8.dp)
            )
        }
        LazyRow(
            state = listState
        ) {
            itemsIndexed(
                items = list
            ) { pos, item ->
                FoodFilterItem(
                    content = item,
                    selected = selected.let {
                        if(it.isNotEmpty()) {
                            it == item
                        } else {
                            pos == 0
                        }
                    },
                    onClick = {
                        onItemClick(item)
                    }
                )
            }
        }
    }
}

@Composable
private fun FoodFilterItem(
    content: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = content,
        color = if(selected) {
            Color.Black
        } else {
            Color.Gray
        },
        modifier = modifier
            .background(
                color = if (selected) {
                    Color.Gray.copy(alpha = 0.2f)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
private fun FoodSlot(
    list: List<Food>,
    pagerState: PagerState,
    foodFilter: FoodType,
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
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .height(300.dp)
                .background(
                    color = SlotFrame,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp)
        ) {
            if(list.isNotEmpty()) {
                SlotInfo(
                    foodCount = list.size,
                    filter = foodFilter,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
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
}

@Composable
private fun SlotInfo(
    foodCount: Int,
    filter: FoodType,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        filter.let {
            SlotInfoFoodFilter(it.materialCategory.ifEmpty { "전체" })
            SlotInfoFoodFilter(it.kindCategory.ifEmpty { "전체" })
            SlotInfoFoodFilter(it.occasionCategory.ifEmpty { "전체" })
        }
        Text(
            text = "${StringUtil.getNumberFormat(foodCount)} 가지 메뉴",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun SlotInfoFoodFilter(content: String) {
    Text(
        text = content,
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
    )
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
        color = SlotDrumContent,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .background(SlotDrumContainer)
            .wrapContentHeight(Alignment.CenterVertically)
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
            text = "준비중",
            color = SlotDrumContent,
            textAlign = TextAlign.Center,
            modifier = modifier
                .wrapContentHeight(Alignment.CenterVertically)
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
            enabled = enabled,
            onClick = onClickStart,
            modifier = Modifier.weight(1f)
        )

        SlotButton(
            text = "Stop",
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
        SlotFoodTreatButton(
            text = "Cook",
            enabled = enabled,
            onClick = onClickCook,
            modifier = Modifier.weight(1f)
        )
        SlotFoodTreatButton(
            text = "Order",
            enabled = enabled,
            onClick = onClickOrder,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SlotButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = enabled,
        colors = ComponentUtil.getSlotButtonColors(),
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text
        )
    }
}

@Composable
private fun SlotFoodTreatButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)

    ShadowIndicatedScaffold(
        modifier = modifier,
        shadowSettings = ShadowSettings(
            shape = shape,
            color = SlotDrumContent.copy(alpha = 0.05f),
            blurDp = 10.dp,
            sideType = ShadowSideType.AllSide(0f, 8f)
        )
    ) {
        Button(
            enabled = enabled,
            colors = ComponentUtil.getSlotFoodTreatButtonColors(),
            shape = shape,
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text
            )
        }
    }
}
