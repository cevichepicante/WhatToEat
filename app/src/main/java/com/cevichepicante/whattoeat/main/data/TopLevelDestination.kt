package com.cevichepicante.whattoeat.main.data

import androidx.annotation.StringRes
import com.cevichepicante.foodpicker.navigation.FoodPickerRoute
import com.cevichepicante.order.navigation.OrderRoute
import com.cevichepicante.recipe.navigation.RecipeRoute
import com.cevichepicante.whattoeat.R
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @StringRes val titleTextId: Int,
    val route: KClass<*>
) {
    PickingFood(
        titleTextId = R.string.tool_bar_title_picking_food,
        route = FoodPickerRoute::class
    ),
    Recipe(
        titleTextId = R.string.tool_bar_title_recipe,
        route = RecipeRoute::class
    ),
    Order(
        titleTextId = R.string.tool_bar_title_order,
        route = OrderRoute::class
    )
}