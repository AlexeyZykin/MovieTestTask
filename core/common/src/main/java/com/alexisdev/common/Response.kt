package com.alexisdev.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
}


fun <T> Flow<T>.asResponse(): Flow<Response<T>> = map<T, Response<T>> { Response.Success(it) }
    .catch { emit(Response.Error(it.message ?: "Error")) }