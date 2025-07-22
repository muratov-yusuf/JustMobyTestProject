package dev.ymuratov.jm_test_project.feature.info.ui.event

sealed interface MovieInfoEvent {
    data object RetryLoadData : MovieInfoEvent
}