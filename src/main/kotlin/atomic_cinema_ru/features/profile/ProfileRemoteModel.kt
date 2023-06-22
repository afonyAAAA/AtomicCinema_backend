package atomic_cinema_ru.features.profile

import atomic_cinema_ru.features.register.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ProfileInfoResponseRemote(
    val login : String,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth : LocalDate,
    val firstName : String,
    val name : String,
    val lastName : String,
    val numberPhone : String,
    val balance : String
)

@Serializable
data class ProfileInfoReceiveRemote(
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth : LocalDate,
    val firstName : String,
    val name : String,
    val lastName : String,
    val numberPhone : String,
    val balance : String
)

@Serializable
data class ProfileEditReceiveRemote(
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth : LocalDate,
    val firstName : String,
    val name : String,
    val lastName : String,
    val numberPhone : String,
)


@Serializable
data class ProfileInfoPaymentsResponse(
   val sumPay : Double,
   val countTickets : Int
)

@Serializable
data class ProfileBalanceReceiveRemote(
    val balance : String
)
