package atomic_cinema_ru.features.genre

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val id : Int,
    val nameGenre : String
)



