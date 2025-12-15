package com.cevichepicante.data.util

import android.content.Context
import androidx.annotation.RawRes
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class CsvHelper @Inject constructor() {

    fun readAll(
        context: Context,
        @RawRes csvResId: Int
    ): List<List<String>> {
        val resourceStream = context.resources.openRawResource(csvResId)
        val reader = BufferedReader(InputStreamReader(resourceStream))
        return CSVReader(reader).use { csv ->
            csv.readAll().map {
                it.toList()
            }
        }
    }
}