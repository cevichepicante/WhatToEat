package com.cevichepicante.whattoeat.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.foodpicker.PickingFoodScreen
import com.cevichepicante.foodpicker.PickingFoodViewModel
import com.cevichepicante.recipe.RecipeScreen
import com.cevichepicante.recipe.RecipeViewModel
import com.cevichepicante.whattoeat.main.navigation.WteNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
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
                    Scaffold { innerPadding ->
                        WteNavHost(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}