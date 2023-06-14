package atomic_cinema_ru.features.seance

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*


fun Application.configureSeanceRouting(){
    routing {
        post("/getSeanceMovie") {
            getSeanceMovieById(call)
        }
        post("/getSeanceCinema") {
            getPresentSeancesCinema(call)
        }
        authenticate {
            post("/addSeance") {
                addSeance(call)
            }
            post("/deleteSeance") {
                deleteSeance(call)
            }
            post("/updateSeance") {
                updateSeance(call)
            }
        }
    }
}