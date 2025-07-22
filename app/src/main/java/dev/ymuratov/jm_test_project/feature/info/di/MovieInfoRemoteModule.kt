package dev.ymuratov.jm_test_project.feature.info.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ymuratov.jm_test_project.feature.info.data.api.MovieInfoApiService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieInfoRemoteModule {

    @Singleton
    @Provides
    fun provideMovieInfoApiService(retrofit: Retrofit): MovieInfoApiService = retrofit.create()
}