package com.cevichepicante.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cevichepicante.model.RecipeMaterialData

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

    recipe?.let {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
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
}

@Composable
fun RecipeMainInfo(
    name: String,
    level: String,
    time: String,
    kindCategory: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Text(
            text = kindCategory,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = time,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = level,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
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

