package atomic_cinema_ru.features.hall

import kotlinx.serialization.Serializable

@Serializable
data class HallResponse(
    val idHall : Int,
    val nameTypeHall : String,
    val capacity : String
)

@Serializable
data class HallReceive(
    val idCinema : Int
)



