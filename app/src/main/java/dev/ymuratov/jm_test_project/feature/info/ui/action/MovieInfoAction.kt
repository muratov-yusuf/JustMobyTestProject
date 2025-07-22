package dev.ymuratov.jm_test_project.feature.info.ui.action

sealed interface MovieInfoAction {

    data class ShowError(val exception: Throwable) : MovieInfoAction
}