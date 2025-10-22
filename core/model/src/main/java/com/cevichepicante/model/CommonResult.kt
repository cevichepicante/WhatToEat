package com.cevichepicante.model

sealed class CommonResult<T> {

    data class Success<T>(
        val data: T
    ): CommonResult<T>()

    data class Failure<T>(
        val errorCode: String,
        val errorMsg: String = ""
    ): CommonResult<T>()
}

inline fun <T> CommonResult<T>.onSuccess(action: (T) -> Unit): CommonResult<T> {
    if(this is CommonResult.Success) {
        action(data)
    }
    return this
}

inline fun <T> CommonResult<T>.onFailure(action: (String, String) -> Unit): CommonResult<T> {
    if(this is CommonResult.Failure) {
        action(errorCode, errorMsg)
    }
    return this
}
