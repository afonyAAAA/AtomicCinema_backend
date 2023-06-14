package atomic_cinema_ru.features.movie

import kotlinx.serialization.Serializable



@Serializable
class MovieAddReceiveRemote(
    val ageRating : String,
    val description : String,
    val director : String,
    val duration : Int,
    val linkImage : String,
    val nameMovie : String,
    val yearOfIssue : String,
    val listGenre : List<Int>
)

@Serializable
class MovieUpdateReceiveRemote(
    val id : Int,
    val ageRating : String,
    val description : String,
    val director : String,
    val duration : Int,
    val linkImage : String,
    val nameMovie : String,
    val yearOfIssue : String,
    val listGenre : List<Int>
)

@Serializable
class MovieDeleteReceiveRemote (
    val id : Int
)

@Serializable
data class MovieFilterReceiveRemote(
    val listGenre: List<String> = listOf(),
    val ageRating: Int?= null,
    val yearOfIssue : Int? = null
)



