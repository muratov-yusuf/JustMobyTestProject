package dev.ymuratov.jm_test_project.feature.info.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ymuratov.jm_test_project.feature.info.data.repository.MovieInfoRepositoryImpl
import dev.ymuratov.jm_test_project.feature.info.domain.repository.MovieInfoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieInfoRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMovieInfoRepository(impl: MovieInfoRepositoryImpl): MovieInfoRepository
}