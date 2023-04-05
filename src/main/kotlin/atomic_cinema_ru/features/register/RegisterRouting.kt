package atomic_cinema_ru.features.register

import atomic_cinema_ru.features.cache.InMemoryCache
import atomic_cinema_ru.features.cache.TokenCache
import atomic_cinema_ru.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDate
import java.util.*

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            RegisterController().registerNewUser(call)
        }
    }

}