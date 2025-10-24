package com.cevichepicante.ui.common

import java.text.NumberFormat
import java.util.Locale

object StringUtil {

    fun getNumberFormat(number: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
        return formatter.format(number)
    }
}