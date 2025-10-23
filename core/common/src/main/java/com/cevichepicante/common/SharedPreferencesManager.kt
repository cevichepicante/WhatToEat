package com.cevichepicante.common

import android.content.Context

object SharedPreferencesManager {

    private const val IsFoodDatabaseInserted = "isFoodDatabaseInserted"

    fun setFoodDatabaseInserted(context: Context, inserted: Boolean) {
        context.getSharedPreferences(IsFoodDatabaseInserted, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(IsFoodDatabaseInserted, inserted)
            .apply()
    }

    fun isFoodDatabaseInserted(context: Context): Boolean {
        return context.getSharedPreferences(IsFoodDatabaseInserted, Context.MODE_PRIVATE)
            .getBoolean(IsFoodDatabaseInserted, false)
    }
}