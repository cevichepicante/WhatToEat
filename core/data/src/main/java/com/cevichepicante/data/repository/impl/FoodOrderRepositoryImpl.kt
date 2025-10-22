package com.cevichepicante.data.repository.impl

import com.cevichepicante.data.repository.FoodOrderRepository
import com.cevichepicante.model.CommonResult
import com.cevichepicante.model.FoodOrderReq
import com.cevichepicante.model.FoodOrderRes
import javax.inject.Inject

class FoodOrderRepositoryImpl @Inject constructor(
    // TODO api service injected
): FoodOrderRepository {

    override suspend fun requestOrder(param: FoodOrderReq): CommonResult<FoodOrderRes> {
        // example
        val orderData = FoodOrderRes(
            foodId = "test",
            address = "서울특별시 금천구",
            clientName = "정소윤",
            price = 28000,
            leadTime = 900,
            delivererNumber = "010-1010-1010"
        )
        return if(orderData != null) {
            CommonResult.Success(
                data = orderData
            )
        } else {
            CommonResult.Failure(
                errorCode = "test"
            )
        }
    }
}