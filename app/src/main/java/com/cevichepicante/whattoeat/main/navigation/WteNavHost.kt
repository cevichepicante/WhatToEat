package com.cevichepicante.whattoeat.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cevichepicante.foodpicker.navigation.FoodPickerBaseRoute
import com.cevichepicante.foodpicker.navigation.FoodPickerRoute
import com.cevichepicante.foodpicker.navigation.pickingFoodScreen
import com.cevichepicante.order.navigation.navigateToOrder
import com.cevichepicante.order.navigation.orderScreen
import com.cevichepicante.recipe.navigation.navigateToRecipe
import com.cevichepicante.recipe.navigation.recipeScreen

@Composable
fun WteNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
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
            onFinishOrder = {
                navController.popBackStack(
                    route = FoodPickerRoute::class,
                    inclusive = false,
                    saveState = false,
                )
            }
        )
    }
}