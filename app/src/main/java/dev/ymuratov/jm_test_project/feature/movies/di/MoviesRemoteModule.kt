package dev.ymuratov.jm_test_project.feature.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ymuratov.jm_test_project.feature.movies.data.api.MoviesApiService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesRemoteModule {

    @Singleton
    @Provides
    fun provideMoviesApiService(retrofit: Retrofit): MoviesApiService = retrofit.create()
}