package com.cevichepicante.order.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cevichepicante.order.OrderScreen
import kotlinx.serialization.Serializable

@Serializable
data class OrderRoute(
    val foodId: String
)

fun NavController.navigateToOrder(foodId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = OrderRoute(foodId),
        builder = navOptions
    )
}

fun NavGraphBuilder.orderScreen(
    onFinishOrder: () -> Unit
) {
    composable<OrderRoute> { entry ->
        val order = entry.toRoute<OrderRoute>()
        OrderScreen(
            foodId = order.foodId,
            viewModel = hiltViewModel(),
            onFinishOrder = onFinishOrder
        )
    }
}