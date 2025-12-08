package com.cevichepicante.model

data class FoodOrderReq(
    val foodId: String = "",
    val address: MandatoryInfo<String> = MandatoryInfo("주소", ""),
    val clientName: MandatoryInfo<String> = MandatoryInfo("이름", ""),
    val clientNumber: MandatoryInfo<String> = MandatoryInfo("전화번호", ""),
    val foodAmount: Int = 0,
    val foodPrice: Int = 0
) {
}

data class MandatoryInfo<T>(
    val field: String,
    val value: T
) {
    fun updated(newValue: T) = this.let {
        if(value != newValue) {
            copy(value = newValue)
        } else {
            this
        }
    }
}

data class FoodOrderRes(
    val orderId: String,
    val foodName: String,
    val foodAmount: Int,
    val address: String,
    val clientName: String,
    val price: Int,
    val leadTime: Int,
    val delivererNumber: String,
)