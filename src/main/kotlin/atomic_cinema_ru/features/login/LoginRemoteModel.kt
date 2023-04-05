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
data class TokenReceiveRemote(
    val login: String,
    val token: String
)

@Serializable
data class TokenResponseRemote(
    val id : Int,
    val login : String,
    val password : String,
    @Serializable(with = LocalDateSerializer::class)
    val dateBirth : LocalDate,
    val firstName : String,
    val name : String,
    val lastName : String,
    val numberPhone : String,
    val idRole : Int
)


