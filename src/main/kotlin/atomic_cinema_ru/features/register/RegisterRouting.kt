package atomic_cinema_ru.features.register

import atomic_cinema_ru.security.hashing.HashingService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRegisterRouting(hashingService: HashingService) {
    routing {
        post("/register") {
           registerNewUser(call, hashingService)
        }
    }
}