package io.github.melkyfb.workshare.data.model

import java.io.Serializable

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val id: String,
    val displayName: String,
    val username: String,
    val password: String
): Serializable {
    companion object {
        val TABLE_NAME = "users"
        val TABLE_COLUMNS = arrayOf(
            "id",
            "display_name",
            "username",
            "password"
        )
        val TABLE_TYPES = arrayOf(
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT"
        )
    }
}
