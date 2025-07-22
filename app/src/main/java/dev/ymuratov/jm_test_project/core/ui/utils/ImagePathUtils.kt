package dev.ymuratov.jm_test_project.core.ui.utils

import dev.ymuratov.jm_test_project.core.Constants

fun String.toImageUrl(): String{
    return Constants.IMAGE_BASE_URL + this
}