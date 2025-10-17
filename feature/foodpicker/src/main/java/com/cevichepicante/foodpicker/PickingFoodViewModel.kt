package com.cevichepicante.foodpicker

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevichepicante.data.repository.FoodSourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickingFoodViewModel @Inject constructor(
    private val repo: FoodSourceRepository,
): ViewModel() {

}