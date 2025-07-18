package dev.ymuratov.jm_test_project.core.data.network.interceptor

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import dev.ymuratov.jm_test_project.core.data.network.exception.NoConnectivityException
import dev.ymuratov.jm_test_project.core.data.network.utils.hasActiveNetwork
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(@param:ApplicationContext private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.hasActiveNetwork()) {
            throw NoConnectivityException()
        }
        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            e.printStackTrace()
            throw NoConnectivityException()
        }
    }
}