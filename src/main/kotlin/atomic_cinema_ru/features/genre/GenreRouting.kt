package atomic_cinema_ru.features.genre

import atomic_cinema_ru.features.security.authenticate
import atomic_cinema_ru.security.hashing.HashingService
import atomic_cinema_ru.security.token.TokenConfig
import atomic_cinema_ru.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGenreRouting() {
    routing {
        get("/allGenre"){
            getAllGenre(call)
        }
    }
}

