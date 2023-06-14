package atomic_cinema_ru.features.movie

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureMovieRouting() {
    routing {
        get("/allMovie") {
           getAllMovie(call)
        }
        post("/filterMovie") {
            getMovieWithFilter(call)
        }
        authenticate{
            post("/updateMovie"){
                updateMovie(call)
            }
            post("/addMovie") {
                addMovie(call)
            }
            post("/deleteMovie") {
                deleteMovie(call)
            }
        }
    }
}