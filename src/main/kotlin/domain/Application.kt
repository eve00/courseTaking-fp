package domain

import kotlinx.serialization.Serializable


@Serializable
data class Application(
    val id: String,
    val courseId: String
){}