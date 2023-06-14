package atomic_cinema_ru.database.seances

import kotlinx.datetime.*
import java.math.BigDecimal

data class SeancesDTO(
    val id : Int = 0,
    val dateEnd : LocalDate,
    val dateStart : LocalDate,
    val timeStart : LocalTime,
    val timeEnd : LocalTime,
    val price : BigDecimal,
    val nameTypeHall : String = "",
    val addressCinema : String = "",
    val idMovie : Int,
    val idHall : Int
)
