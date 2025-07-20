package dev.ymuratov.jm_test_project.feature.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ymuratov.jm_test_project.feature.movies.data.repository.MoviesRepositoryImpl
import dev.ymuratov.jm_test_project.feature.movies.domain.repository.MoviesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}