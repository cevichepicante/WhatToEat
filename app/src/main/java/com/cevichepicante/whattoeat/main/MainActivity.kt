package com.cevichepicante.whattoeat.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.rememberNavController
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.ui.common.CommonToolBar
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
                    var topLevel by remember { mutableStateOf<TopLevelDestination?>(null) }
                    val navController = rememberNavController()
                    val currentDestination = navController
                        .currentBackStackEntryFlow
                        .collectAsState(null)
                        .value?.destination
                    val onRequestPopBackStack by rememberUpdatedState {
                        // TODO popping back stack with inclusive false is the best way.
                        if(topLevel != null && topLevel != TopLevelDestination.PickingFood) {
                            navController.popBackStack()
                            true
                        } else {
                            false
                        }
                    }

                    LaunchedEffect(currentDestination) {
                        topLevel = TopLevelDestination.entries.find {
                            currentDestination?.hasRoute(route = it.route) == true
                        }
                    }

                    BackHandler {
                        if(!onRequestPopBackStack()) {
                            /*
                                start destination 이 한 개, 즉 root 화면이라
                                popBackStack 을 하지 않은 경우 Activity 종료.
                             */
                            finish()
                        }
                    }

                    Scaffold { innerPadding ->
                        Column(
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            val title = topLevel?.let {
                                stringResource(it.titleTextId)
                            }.orEmpty()

                            CommonToolBar(
                                title = title,
                                onBack = {
                                    onRequestPopBackStack()
                                },
                            )
                            WteNavHost(
                                navController = navController,
                            )
                        }
                    }
                }
            }
        }
    }
}