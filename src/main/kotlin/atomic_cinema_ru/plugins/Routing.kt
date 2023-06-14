package atomic_cinema_ru.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/sayMyName") {
            call.respondText("Афанасьев Александр, гр.602")
        }
    }
}
