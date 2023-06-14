package atomic_cinema_ru.features.login

import atomic_cinema_ru.features.register.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class LoginReceiveRemote(
    val login: String,
    val password: String
)

@Serializable
data class LoginResponseRemote(
    val token: String
)

@Serializable
data class SecretInfoResponseRemote(
    val id : Int,
    val role : String
)



