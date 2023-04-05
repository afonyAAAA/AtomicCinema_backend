package atomic_cinema_ru.database.movies

import atomic_cinema_ru.database.movies.Movies.autoIncrement
import kotlinx.serialization.Serializable

@Serializable
class MovieDTO (
    val id : Int = 0,
    val ageRating : String,
    val description : String,
    val director : String,
    val duration : Int,
    val linkImage : String = "",
    val nameMovie : String,
    val yearOfIssue : String
)