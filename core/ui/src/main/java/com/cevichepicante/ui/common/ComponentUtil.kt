package com.cevichepicante.ui.common

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.cevichepicante.ui.value.SlotButtonContainer
import com.cevichepicante.ui.value.SlotButtonContent
import com.cevichepicante.ui.value.SlotButtonDisabledContainer
import com.cevichepicante.ui.value.SlotButtonDisabledContent
import com.cevichepicante.ui.value.SlotFilterSetButtonContainer
import com.cevichepicante.ui.value.SlotFilterSetButtonContent
import com.cevichepicante.ui.value.SlotFoodTreatButtonContainer
import com.cevichepicante.ui.value.SlotFoodTreatButtonContent
import com.cevichepicante.ui.value.SlotFoodTreatButtonDisabledContent

object ComponentUtil {

    @Composable
    fun Int.asDp(): TextUnit {
        with(LocalDensity.current) {
            return this@asDp.dp.toSp()
        }
    }

    @Composable
    fun getSlotButtonColors(): ButtonColors {
        return ButtonColors(
            containerColor = SlotButtonContainer,
            contentColor = SlotButtonContent,
            disabledContainerColor = SlotButtonDisabledContainer,
            disabledContentColor = SlotButtonDisabledContent
        )
    }

    @Composable
    fun getSlotFoodTreatButtonColors(): ButtonColors {
        return ButtonColors(
            containerColor = SlotFoodTreatButtonContainer,
            contentColor = SlotFoodTreatButtonContent,
            disabledContainerColor = SlotFoodTreatButtonContainer,
            disabledContentColor = SlotFoodTreatButtonDisabledContent
        )
    }

    @Composable
    fun getSlotFilterSetButtonColors(): ButtonColors {
        return ButtonColors(
            containerColor = SlotFilterSetButtonContainer,
            contentColor = SlotFilterSetButtonContent,
            disabledContainerColor = SlotFilterSetButtonContent,
            disabledContentColor = SlotFilterSetButtonContent
        )
    }
}