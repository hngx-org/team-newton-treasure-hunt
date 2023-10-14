package com.teamnewton.treasurehunt.app.repository

sealed class NetworkResults<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Loading<T>: NetworkResults<T>()

    class Success<T>(data: T?): NetworkResults<T>(data = data)

    class Error<T>(throwable: Throwable?): NetworkResults<T>(throwable = throwable)
}