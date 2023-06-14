package atomic_cinema_ru.database.numbersPhoneCinema

import kotlinx.serialization.Serializable


@Serializable
data class CinemaNumbersPhoneDTO(
    val cinemaID : Int,
    val numberPhone : String
)
