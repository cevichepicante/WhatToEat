package com.cevichepicante.data.repository

import com.cevichepicante.model.CommonResult
import com.cevichepicante.model.FoodOrderReq
import com.cevichepicante.model.FoodOrderRes

interface FoodOrderRepository {

    suspend fun requestOrder(param: FoodOrderReq): CommonResult<FoodOrderRes>
}