package com.cevichepicante.foodpicker

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickingFoodViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: FoodSourceRepository,
): ViewModel() {

    private val _foodList = MutableStateFlow<List<Food>>(listOf())
    val foodList: StateFlow<List<Food>>
        get() = _foodList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchFoodList().let {
                _foodList.emit(it)
            }
        }
    }
}