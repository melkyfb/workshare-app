package io.github.melkyfb.workshare.data.model

import java.io.Serializable

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val finished: Boolean,
    val ownerUser: LoggedInUser
): Serializable