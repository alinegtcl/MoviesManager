package com.luisitolentino.moviesmanager.domain.utils

sealed class MMResult<out S, out E> {
    data class Success<out S>(val data: S) : MMResult<S, Nothing>()
    data class Error<out E>(val failure: E) : MMResult<Nothing, E>()
}

inline fun <S, E, R> MMResult<S, E>.flow(
    success: (S) -> R,
    error: (E) -> R
): R {
    return when (this) {
        is MMResult.Success -> success(data)
        is MMResult.Error -> error(failure)
    }
}