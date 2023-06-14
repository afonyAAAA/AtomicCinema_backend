package atomic_cinema_ru.features.cinema

import kotlinx.serialization.Serializable

@Serializable
data class CinemaResponseRemote(
    val id : Int,
    val addressCinema : String,
    val numbersPhone : List<String>
)

@Serializable
data class RevenueReportResponseRemote(
    val sumMoney : String,
    val sumTickets : String
)
