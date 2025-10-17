package com.cevichepicante.data.util

object StringUtil {

    fun String.removeInvisibleChars(): String {
        return this.replace(Regex("[\\[{Z}\\p{C}]"), "")
    }
}