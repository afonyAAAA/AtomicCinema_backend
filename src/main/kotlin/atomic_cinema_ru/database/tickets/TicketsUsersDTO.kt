package atomic_cinema_ru.database.tickets

import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class TicketsUsersDTO(
    val id : Int = 0,
    val idUser : Int = 0,
    val idSeance : Int = 0,
    val nameStatus : String,
    val count : Int,
    val dateTime : LocalDateTime,
    val returned : Boolean,
    val addressCinema : String,
    val nameMovie : String,
    val linkImage : String,
    val duration : Int,
    val ageRating : String,
    val timeStart : LocalTime,
    val timeEnd : LocalTime,
    val price : Double,
    val nameTypeHall : String,
    val dateStartSeance : LocalDate
)
