package dev.ymuratov.jm_test_project.core.data.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dev.ymuratov.jm_test_project.core.data.network.exception.ServerException
import dev.ymuratov.jm_test_project.core.data.network.exception.EmptyBodyException
import retrofit2.Response
import java.io.IOException

inline fun <R> remoteRequest(requestBody: () -> Result<R>): Result<R> {
    return try {
        requestBody()
    } catch (e: IOException) {
        Result.failure(e)
    }
}

fun <T> Response<T>.handle(): Result<T> {
    return handle { it }
}

inline fun <T, R> Response<T>.handle(
    onError: (response: Response<T>) -> Result<R>? = { null }, transform: ((body: T) -> R)
): Result<R> {
    return if (isSuccessful) {
        mapBody(transform)
    } else {
        val exception = ServerException(code(), message())
        onError(this) ?: Result.failure(exception)
    }
}

inline fun <T, R> Response<T>.mapBody(transform: ((body: T) -> R)): Result<R> {
    val body = body()
    return if (body != null) {
        Result.success(transform(body))
    } else {
        Result.failure(EmptyBodyException())
    }
}

fun Context.hasActiveNetwork(): Boolean {
    var result = false
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.run {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
    return result
}