package com.cevichepicante.recipe.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cevichepicante.recipe.RecipeScreen
import kotlinx.serialization.Serializable

@Serializable
data class RecipeRoute(
    val foodId: String
)

fun NavController.navigateToRecipe(foodId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = RecipeRoute(
            foodId = foodId
        ),
        builder = navOptions
    )
}

fun NavGraphBuilder.recipeScreen(
    onCloseClick: () -> Unit
) {
    composable<RecipeRoute> { entry ->
        val recipe = entry.toRoute<RecipeRoute>()
        RecipeScreen(
            foodId = recipe.foodId,
            viewModel = hiltViewModel(),
            onClickClose = onCloseClick
        )
    }
}

