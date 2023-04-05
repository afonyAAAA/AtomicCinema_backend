package atomic_cinema_ru.features.movie

import atomic_cinema_ru.database.movies.Movies
import io.ktor.server.application.*
import io.ktor.server.response.*

class MovieController {
    suspend fun getAllMovie(call: ApplicationCall) {
        call.respond(Movies.getAllMovie())
    }
}