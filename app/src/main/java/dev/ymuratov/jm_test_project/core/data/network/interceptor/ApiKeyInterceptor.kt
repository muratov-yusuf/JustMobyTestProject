package dev.ymuratov.jm_test_project.core.data.network.interceptor

import dev.ymuratov.jm_test_project.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
            .addHeader("accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}