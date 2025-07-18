package dev.ymuratov.jm_test_project.core.data.network.exception

import dev.ymuratov.jm_test_project.core.data.AppException

open class ServerException(
    val code: Int,
    override val message: String? = null
) : AppException()
