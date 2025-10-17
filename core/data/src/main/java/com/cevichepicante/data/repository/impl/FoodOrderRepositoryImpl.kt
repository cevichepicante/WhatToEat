package com.cevichepicante.data.repository.impl

import com.cevichepicante.data.repository.FoodOrderRepository
import javax.inject.Inject

class FoodOrderRepositoryImpl @Inject constructor(
    // TODO api service injected
): FoodOrderRepository {

    override suspend fun requestOrder() {
        TODO("Not yet implemented")
    }
}