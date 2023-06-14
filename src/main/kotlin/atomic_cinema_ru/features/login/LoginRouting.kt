package atomic_cinema_ru.features.login

import atomic_cinema_ru.features.security.authenticate
import atomic_cinema_ru.security.hashing.HashingService
import atomic_cinema_ru.security.token.TokenConfig
import atomic_cinema_ru.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureLoginRouting(tokenConfig : TokenConfig, hashingService: HashingService, tokenService: TokenService) {
    routing {
        post("/login") {
           performLogin(call, hashingService, tokenService, tokenConfig)
        }
        post("/goOut"){
            deactivateToken(call)
        }
        authenticate()
    }
}

