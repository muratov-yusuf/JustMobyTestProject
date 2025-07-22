package dev.ymuratov.jm_test_project.feature.movies.ui.event

sealed interface MoviesEvent {

    data object RetryLoadData : MoviesEvent

}