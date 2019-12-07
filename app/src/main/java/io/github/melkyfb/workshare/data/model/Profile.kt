package io.github.melkyfb.workshare.data.model

data class Profile (
    val id: String,
    val user: LoggedInUser,
    val email: String,
    val picturePath: String,
    val bio: String,
    val local: String
)