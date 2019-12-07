package io.github.melkyfb.workshare.data.model

data class Contributor (
    val id: String,
    val contributor: LoggedInUser,
    val project: Project
)