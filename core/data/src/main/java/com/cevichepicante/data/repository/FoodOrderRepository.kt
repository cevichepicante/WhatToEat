package com.cevichepicante.data.repository

interface FoodOrderRepository {

    //  TODO: request param
    abstract suspend fun requestOrder()
}