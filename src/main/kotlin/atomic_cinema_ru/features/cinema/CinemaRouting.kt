package atomic_cinema_ru.features.cinema

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureCinemaRouting(){
    routing {
        get("/allCinema") {
            getAllCinema(call)
        }
        authenticate {
            get("/revenueReport") {
                formRevenueReport(call)
            }
        }
    }
}