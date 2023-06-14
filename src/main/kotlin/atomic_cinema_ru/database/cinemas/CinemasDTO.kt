package atomic_cinema_ru.database.cinemas

import atomic_cinema_ru.database.numbersPhoneCinema.CinemaNumbersPhoneDTO
import kotlinx.serialization.Serializable

@Serializable
data class CinemasDTO(
    val id : Int,
    val addressCinema : String,
    val numbersPhone : List<CinemaNumbersPhoneDTO?>
)
