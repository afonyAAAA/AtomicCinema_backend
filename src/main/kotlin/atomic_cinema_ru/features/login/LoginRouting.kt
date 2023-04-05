package atomic_cinema_ru.features.login

import atomic_cinema_ru.features.cache.InMemoryCache
import atomic_cinema_ru.features.cache.TokenCache
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            LoginController().performLogin(call)
        }
        post("/login/performToken") {
            LoginController().performLoginWithToken(call)
        }
    }
}