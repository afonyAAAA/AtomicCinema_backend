package atomic_cinema_ru.features.seance

import atomic_cinema_ru.features.register.BigDecimalSerializer
import atomic_cinema_ru.features.register.LocalDateSerializer
import atomic_cinema_ru.features.register.LocalTimeSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime


@Serializable
data class SeanceReceiveRemote(
    val idMovie : Int
)

@Serializable
data class SeanceDeleteReceiveRemote(
    val id : Int
)

@Serializable
data class SeancesCinema(
    val addressCinema: String
)

@Serializable
data class SeanceResponseRemote(
    val id : Int,
    @Serializable(with = LocalDateSerializer::class)
    val dateEnd : LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val dateStart : LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val timeStart : LocalTime,
    @Serializable(with = LocalTimeSerializer::class)
    val timeEnd : LocalTime,
    @Serializable(with = BigDecimalSerializer::class)
    val price : BigDecimal,
    val nameTypeHall : String,
    val addressCinema : String,
    val idMovie : Int,
    val idHall : Int
)

@Serializable
data class SeanceAddReceiveRemote(
    @Serializable(with = LocalDateSerializer::class)
    val dateEnd : LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val dateStart : LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val timeStart : LocalTime,
    @Serializable(with = LocalTimeSerializer::class)
    val timeEnd : LocalTime,
    val price : String,
    val idMovie : Int,
    val idHall : Int
)

@Serializable
data class SeanceUpdateReceiveRemote(
    val id : Int,
    @Serializable(with = LocalDateSerializer::class)
    val dateEnd : LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val dateStart : LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    val timeStart : LocalTime,
    @Serializable(with = LocalTimeSerializer::class)
    val timeEnd : LocalTime,
    val price : String,
    val idMovie : Int,
    val idHall : Int
)
