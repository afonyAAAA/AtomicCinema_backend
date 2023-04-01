package atomic_cinema_ru.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val login : String,
    val password : String,
    val email : String
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)
