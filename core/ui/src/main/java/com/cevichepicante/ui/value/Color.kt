package com.cevichepicante.ui.value

import androidx.compose.ui.graphics.Color

private val SlotBaseColor = Color(0xFFF7EBD5)
private val ContentBaseColor = Color.Black.copy(0.8f)
private val ContentDisabledBaseColor = Color.Gray.copy(alpha = 0.6f)
val SlotFrame = SlotBaseColor.copy(alpha = 0.8f)
val SlotFoodAmount = Color.Gray
val SlotDrumContainer = Color.White
val SlotDrumContent = Color(0xFF673F00)
val SlotButtonContent = ContentBaseColor
val SlotButtonContainer = Color.Blue.copy(alpha = 0.2f)
val SlotButtonDisabledContent = ContentDisabledBaseColor
val SlotButtonDisabledContainer = SlotButtonDisabledContent.copy(alpha = 0.5f)

val SlotFoodTreatButtonContent = ContentBaseColor
val SlotFoodTreatButtonDisabledContent = ContentDisabledBaseColor
val SlotFoodTreatButtonContainer = SlotBaseColor

val SlotFilterCategory = Color.Black
val SlotFilterSelectedOption = Color.Black
val SlotFilterSetButtonContent = ContentBaseColor
val SlotFilterSetButtonDisabledContent = ContentDisabledBaseColor
val SlotFilterSetButtonContainer = SlotButtonContainer
val SlotFilterSetButtonDisabledContainer = SlotFilterSetButtonDisabledContent.copy(alpha = 0.5f)


