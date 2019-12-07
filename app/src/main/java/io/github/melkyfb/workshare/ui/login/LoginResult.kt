package io.github.melkyfb.workshare.ui.login

import io.github.melkyfb.workshare.data.model.LoggedInUser

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUser? = null,
    val error: Int? = null
)
