package dev.ymuratov.jm_test_project.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ymuratov.jm_test_project.BuildConfig
import dev.ymuratov.jm_test_project.core.Constants
import dev.ymuratov.jm_test_project.core.data.network.interceptor.NetworkConnectionInterceptor
import kotlinx.serialization.json.Json
import okhttp3.ConnectionSpec
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val OK_HTTP_TIMEOUT = 10L

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkInterceptor: NetworkConnectionInterceptor, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(networkInterceptor).addInterceptor(loggingInterceptor)
            .connectTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS).readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    @Named("wsClient")
    fun provideWsOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectionSpecs(listOf(ConnectionSpec.RESTRICTED_TLS))
            .writeTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()

    @Singleton
    @Provides
    fun provideRetrofitClient(json: Json, okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder().addConverterFactory(json.asConverterFactory(contentType)).baseUrl(Constants.BASE_URL)
            .client(okHttpClient).build()
    }
}