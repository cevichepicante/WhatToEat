package com.cevichepicante.data.repository.impl

import android.os.SystemClock
import com.cevichepicante.common.FormatUtil
import com.cevichepicante.data.repository.FoodOrderRepository
import com.cevichepicante.database.dao.FoodDao
import com.cevichepicante.database.dao.OrderHistoryDao
import com.cevichepicante.database.model.OrderHistoryEntity
import com.cevichepicante.database.model.asFoodModel
import com.cevichepicante.model.CommonResult
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodOrderReq
import com.cevichepicante.model.FoodOrderRes
import com.cevichepicante.model.FoodType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoodOrderRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao,
    private val orderDao: OrderHistoryDao
): FoodOrderRepository {

    override suspend fun requestOrder(param: FoodOrderReq): CommonResult<FoodOrderRes> {
        val foodName = withContext(Dispatchers.IO) {
            foodDao.getFoodName(param.foodId).orEmpty()
        }
        // example of server response data
        val orderData = FoodOrderRes(
            orderId = SystemClock.currentThreadTimeMillis().toString(),
            foodName = foodName,
            foodAmount = param.foodAmount,
            address = param.address.value,
            clientName = param.clientName.value,
            price = param.foodPrice.times(param.foodAmount),
            leadTime = 900,
            delivererNumber = "010-1010-1010"
        )

        return if(orderData != null) {
            withContext(Dispatchers.IO) {
                orderDao.insertOrderHistory(
                    history = OrderHistoryEntity(
                        id = orderData.orderId,
                        foodId = param.foodId,
                        foodAmount = param.foodAmount,
                        clientName = orderData.clientName,
                        address = orderData.address,
                        price = orderData.price,
                        orderDate = System.currentTimeMillis().toString(),
                        delivererNumber = orderData.delivererNumber
                    )
                )
            }
            CommonResult.Success(
                data = orderData
            )
        } else {
            CommonResult.Failure(
                errorCode = "test"
            )
        }
    }

    override suspend fun fetchOrderFoodInfo(foodId: String): Food? {
        return foodDao.getFoodDetail(foodId)?.asFoodModel()
    }
}