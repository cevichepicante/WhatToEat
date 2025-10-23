package com.cevichepicante.whattoeat

import android.app.Application
import android.util.Log
import com.cevichepicante.common.SharedPreferencesManager
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.database.dao.FoodDao
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class WteApplication: Application() {

    @Inject lateinit var foodSourceRepo: FoodSourceRepository

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            foodSourceRepo.insertFoodSourceList()
        }
    }
}