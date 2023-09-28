package com.joaoreis.codewars

sealed class Result<R> {
    data class Success<R>(val data: R) : Result<R>()
    class Error<R> : Result<R>()
}