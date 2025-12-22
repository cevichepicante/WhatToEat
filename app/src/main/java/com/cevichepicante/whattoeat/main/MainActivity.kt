package com.cevichepicante.whattoeat.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.rememberNavController
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.ui.common.CommonToolBar
import com.cevichepicante.whattoeat.R
import com.cevichepicante.whattoeat.main.data.TopLevelDestination
import com.cevichepicante.whattoeat.main.navigation.WteNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var foodSourceRepo: FoodSourceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            val inserting = withContext(Dispatchers.IO) {
                async {
                    foodSourceRepo.insertFoodSourceList()
                }
            }
            if(inserting.await()) {
                setContent {
                    var toolBarTitle by remember {
                        mutableIntStateOf(R.string.tool_bar_title_picking_food)
                    }
                    val navController = rememberNavController()
                    val currentDestination = navController.currentBackStackEntryFlow
                        .collectAsState(null).value?.destination

                    LaunchedEffect(currentDestination) {
                        TopLevelDestination.entries.find {
                            currentDestination?.hasRoute(route = it.route) == true
                        }?.let {
                            toolBarTitle = it.titleTextId
                        }
                    }

                    Scaffold { innerPadding ->
                        CommonToolBar(
                            title = stringResource(toolBarTitle),
                            onBack = {},
                        )
                        WteNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}