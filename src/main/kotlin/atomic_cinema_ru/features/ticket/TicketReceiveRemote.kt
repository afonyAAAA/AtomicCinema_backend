package atomic_cinema_ru.features.ticket

import atomic_cinema_ru.features.register.LocalDateSerializer
import atomic_cinema_ru.features.register.LocalDateTimeSerializer
import atomic_cinema_ru.features.register.LocalTimeSerializer

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class TicketReceiveCreateTicket(
    val count : Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTime : LocalDateTime,
    val idUser : Int,
    val idStatusPayment : Int,
    val idSeance : Int,
    val returned : Boolean,
    @Serializable(with = LocalDateSerializer::class)
    val dateStartSeance : LocalDate
)

@Serializable
data class TicketReceiveUpdateTicket(
   val idTicket : Int,
   val idStatusPayment : Int,
   val returned: Boolean
)

@Serializable
data class TicketsUserReceiveRemote(
    val login : String,
)

@Serializable
data class TicketResponseRemote(
    val id : Int = 0,
    val idSeance : Int = 0,
    val idUser : Int = 0,
    val nameStatus : String,
    val count : Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTime : LocalDateTime,
    val returned : Boolean,
    val addressCinema : String,
    val nameMovie : String,
    val linkImage : String,
    val duration : Int,
    val ageRating : String,
    @Serializable(with = LocalTimeSerializer::class)
    val timeStart : LocalTime,
    @Serializable(with = LocalTimeSerializer::class)
    val timeEnd : LocalTime,
    val price : Double,
    val nameTypeHall : String,
    @Serializable(with = LocalDateSerializer::class)
    val dateStartSeance : LocalDate
)