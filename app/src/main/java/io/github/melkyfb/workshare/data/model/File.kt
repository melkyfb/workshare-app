package io.github.melkyfb.workshare.data.model

import java.io.Serializable

data class File(
    val id: String,
    val name: String,
    val path: String,
    val version: String,
    val description: String,
    val project: Project,
    val user: LoggedInUser
): Serializable