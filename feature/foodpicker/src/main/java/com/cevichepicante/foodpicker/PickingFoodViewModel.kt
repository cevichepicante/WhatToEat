package com.cevichepicante.foodpicker

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodType
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

    private val _foodFilter = MutableStateFlow(FoodType("", "", ""))
    val foodFilter: StateFlow<FoodType>
        get() = _foodFilter.asStateFlow()

    private val _cookingMaterialList = MutableStateFlow<List<String>>(listOf())
    val cookingMaterialList: StateFlow<List<String>>
        get() = _cookingMaterialList.asStateFlow()

    private val _cookingKindList = MutableStateFlow<List<String>>(listOf())
    val cookingKindList: StateFlow<List<String>>
        get() = _cookingKindList.asStateFlow()

    private val _cookingOccasionList = MutableStateFlow<List<String>>(listOf())
    val cookingOccasionList: StateFlow<List<String>>
        get() = _cookingOccasionList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchFoodList().let {
                _foodList.emit(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchCookingKindList().let {
                _cookingKindList.emit(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchCookingMaterialList().let {
                _cookingMaterialList.emit(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchCookingOccasionList().let {
                _cookingOccasionList.emit(it)
            }
        }
    }

    fun setFoodFilter(type: FoodType) {
        viewModelScope.launch(Dispatchers.IO) {
            _foodFilter.emit(type)

            repo.fetchFoodListFiltered(type).let {
                _foodList.emit(it)
            }
        }
    }
}