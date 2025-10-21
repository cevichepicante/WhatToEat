package com.cevichepicante.recipe

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.model.FoodRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: FoodSourceRepository
): ViewModel() {

    private val _recipeData = MutableStateFlow<FoodRecipe?>(null)
    val recipeData: StateFlow<FoodRecipe?>
        get() = _recipeData.asStateFlow()

    fun getRecipe(foodId: String) {
        viewModelScope.launch {
            repo.fetchRecipe(
                context = context,
                foodId = foodId
            ).let {
                _recipeData.emit(it)
            }
        }
    }
}