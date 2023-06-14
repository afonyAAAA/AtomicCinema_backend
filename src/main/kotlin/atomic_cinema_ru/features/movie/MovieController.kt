package atomic_cinema_ru.features.movie

import atomic_cinema_ru.database.movieGenres.MovieGenres
import atomic_cinema_ru.database.movies.MovieDTO
import atomic_cinema_ru.database.movies.Movies
import atomic_cinema_ru.database.movies.MoviesView
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun getAllMovie(call: ApplicationCall) {
    call.respond(MoviesView.getAllMovie())
}

suspend fun getMovieWithFilter(call : ApplicationCall){
    val receive = call.receive<MovieFilterReceiveRemote>()

    val listMovie = MoviesView.getAllMovie()

    val filterMovie : List<MovieDTO> = listMovie.filter { movie ->
        receive.listGenre.isEmpty() || movie.genresMovie.any { receive.listGenre.contains(it) }
    }.filter { movie ->
        receive.ageRating?.let {movie.ageRating.replace("+", "").toInt() <= it } ?: true
    }.filter { movie ->
        receive.yearOfIssue?.let { movie.yearOfIssue.toInt() == it } ?: true
    }

    call.respond(filterMovie)
}


suspend fun updateMovie(call: ApplicationCall){

    val receive = call.receive<MovieUpdateReceiveRemote>()

    val movieDTO = MovieDTO(
        id = receive.id,
        nameMovie = receive.nameMovie,
        description = receive.description,
        director = receive.director,
        duration = receive.duration,
        ageRating = receive.ageRating,
        yearOfIssue = receive.yearOfIssue,
        linkImage = receive.linkImage
    )

    val result = Movies.update(movieDTO) as MovieDTO

    val resultDeleteGenre = MovieGenres.deleteAllGenresMovie(receive.id)

    if(resultDeleteGenre){
        MovieGenres.insert(result.id, receive.listGenre)
        call.respond(HttpStatusCode.OK)
    }else{
        call.respond(HttpStatusCode.InternalServerError)
    }
}

suspend fun addMovie(call: ApplicationCall){
    val receive = call.receive<MovieAddReceiveRemote>()


    val movieDTO = MovieDTO(
        nameMovie = receive.nameMovie,
        description = receive.description,
        director = receive.director,
        duration = receive.duration,
        ageRating = receive.ageRating,
        yearOfIssue = receive.yearOfIssue,
        linkImage = receive.linkImage,
        genresId = receive.listGenre.toMutableList()
    )

    println(movieDTO.genresId.joinToString())

    val resultIdRow = Movies.insertMovie(movieDTO)

    if(resultIdRow != null){
        MovieGenres.insert(resultIdRow, movieDTO.genresId)
    }

    if(resultIdRow != null) call.respond(HttpStatusCode.OK)
        else call.respond(HttpStatusCode.InternalServerError)

}

suspend fun deleteMovie(call: ApplicationCall) {
    val receive = call.receive<MovieDeleteReceiveRemote>()

    val result = Movies.deleteMovie(receive.id)

    if(result) call.respond(HttpStatusCode.OK)
    else call.respond(HttpStatusCode.InternalServerError)
}

