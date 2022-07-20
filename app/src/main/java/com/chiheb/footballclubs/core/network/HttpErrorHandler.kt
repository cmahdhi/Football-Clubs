package com.chiheb.footballclubs.core.network

suspend fun <T> runSafeApiCall(apiCall: suspend () -> Result<T>): Result<T> {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        Result.failure(NetworkException())
    }
}
