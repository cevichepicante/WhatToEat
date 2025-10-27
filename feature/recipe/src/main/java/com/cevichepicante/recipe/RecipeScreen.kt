package com.cevichepicante.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cevichepicante.model.RecipeMaterialData
import com.cevichepicante.ui.util.ComponentUtil.asDp
import com.cevichepicante.ui.value.SlotFrame

@Composable
fun RecipeScreen(
    foodId: String,
    viewModel: RecipeViewModel,
    onClickClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val recipe by viewModel.recipeData.collectAsState()

    LaunchedEffect(foodId) {
        viewModel.getRecipe(foodId)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(50.dp)
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
                        onClickClose()
                    }
            )
        }

        recipe?.let {
            RecipeMainInfo(
                name = it.name,
                level = it.level,
                time = it.time,
                kindCategory = it.foodType,
                modifier = Modifier.fillMaxWidth()
            )
            RecipeMaterialInfo(
                materialList = it.materialList,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    recipe?.let {
    }
}

@Composable
fun RecipeMainInfo(
    name: String,
    level: String,
    time: String,
    kindCategory: String,
    modifier: Modifier = Modifier
) {
    val mainInfos = listOf(name, level, time)
    
    Column(modifier = modifier) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            fontSize = 20.asDp(),
            lineHeight = 24.asDp(),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        RecipeMainInfoForm(
            category = "주재료",
            content = kindCategory,
        )
        RecipeMainInfoForm(
            category = "소요시간",
            content = time
        )
        RecipeMainInfoForm(
            category = "난이도",
            content = level,
        )
    }
}

@Composable
fun RecipeMainInfoForm(
    category: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$category: $content",
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun RecipeMaterialInfo(
    materialList: List<RecipeMaterialData>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .padding(20.dp)
    ) {
        materialList.forEach {
            RecipeMaterialForm(
                category = it.category,
                contents = it.list,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun RecipeMaterialForm(
    category: String,
    contents: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "[$category]",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        contents.forEach {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp)
            )
        }
    }
}

