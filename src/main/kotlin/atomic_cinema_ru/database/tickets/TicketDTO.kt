package atomic_cinema_ru.database.tickets

import java.time.LocalDate
import java.time.LocalDateTime

data class TicketDTO(
    val id : Int = 0,
    val count : Int,
    val dateTime : LocalDateTime,
    val returned : Boolean = false,
    val idUser : Int,
    val idStatusPayment : Int,
    val idSeance : Int,
    val dateStartSeance : LocalDate
)
