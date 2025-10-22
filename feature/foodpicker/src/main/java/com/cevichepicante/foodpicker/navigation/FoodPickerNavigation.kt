package com.cevichepicante.foodpicker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cevichepicante.foodpicker.PickingFoodScreen
import kotlinx.serialization.Serializable

@Serializable
data object FoodPickerBaseRoute

@Serializable
data object FoodPickerRoute

fun NavController.navigateToFoodPicker(navOptions: NavOptions) {
    navigate(
        route = FoodPickerRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pickingFoodScreen(
    onClickRecipe: (String) -> Unit,
    onClickOrder: (String) -> Unit
) {
    navigation<FoodPickerBaseRoute>(
        startDestination = FoodPickerRoute
    ) {
        composable<FoodPickerRoute> { entry ->
            PickingFoodScreen(
                onClickRecipe = onClickRecipe,
                onClickOrder = onClickOrder,
                viewModel = hiltViewModel()
            )
        }
    }
}
