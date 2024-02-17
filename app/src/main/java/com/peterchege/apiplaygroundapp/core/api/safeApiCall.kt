package com.peterchege.apiplaygroundapp.core.api

import kotlinx.serialization.json.Json
import retrofit2.Response
import timber.log.Timber

inline fun <T : Any, reified E : Any> safeApiCall(
    execute: () -> Response<T>
): NetworkResult<T,E> {
    val TAG = "Network Error"
    return try {
        val response = execute()
        val body = response.body()
        val errorBody = response.errorBody()?.charStream()!!.readText()
        Timber.tag(TAG).i("Response Body >>>>>>>>  $body")
        Timber.tag(TAG).i("Error Body >>>>>>>>  $errorBody")

        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            val error = Json.decodeFromString<E>(errorBody)
            Timber.tag(TAG).i("if else The Error object causing this is -----> $error")
            NetworkResult.Error(errorData = error)
        }
    } catch (e: Throwable) {
        Timber.tag(TAG).i("The Exception causing this is -----> $e")

        NetworkResult.Exception(e)
    }
}



sealed class NetworkResult<T : Any, E : Any> {
    class Success<T : Any, E : Any>(val data: T) : NetworkResult<T, E>()
    class Error<T : Any, E : Any>(val errorData: E) : NetworkResult<T, E>()
    class Exception<T : Any, E : Any>(val e: Throwable) : NetworkResult<T, E>()
}