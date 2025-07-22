package dev.ymuratov.jm_test_project.feature.movies.ui.action

sealed interface MoviesAction {

    data class ShowError(val exception: Throwable) : MoviesAction
}