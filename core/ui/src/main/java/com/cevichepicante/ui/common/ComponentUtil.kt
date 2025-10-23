package com.cevichepicante.ui.common

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

object ComponentUtil {

    @Composable
    fun Int.asDp(): TextUnit {
        with(LocalDensity.current) {
            return this@asDp.dp.toSp()
        }
    }
}