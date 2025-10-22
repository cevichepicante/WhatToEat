package com.cevichepicante.whattoeat.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cevichepicante.foodpicker.navigation.FoodPickerBaseRoute
import com.cevichepicante.foodpicker.navigation.FoodPickerRoute
import com.cevichepicante.foodpicker.navigation.pickingFoodScreen
import com.cevichepicante.order.navigation.navigateToOrder
import com.cevichepicante.order.navigation.orderScreen
import com.cevichepicante.recipe.navigation.navigateToRecipe
import com.cevichepicante.recipe.navigation.recipeScreen

@Composable
fun WteNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = FoodPickerBaseRoute,
        modifier = modifier
    ) {
        pickingFoodScreen(
            onClickRecipe = navController::navigateToRecipe,
            onClickOrder = navController::navigateToOrder,
        )
        recipeScreen(
            onCloseClick = navController::popBackStack
        )
        orderScreen(
            onFinishOrder = navController::popBackStack
        )
    }
}