package atomic_cinema_ru.features.movie

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureMovieRouting() {
    routing {
        get("/allMovie") {
           MovieController().getAllMovie(call)
        }
    }

}