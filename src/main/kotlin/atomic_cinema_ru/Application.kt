package atomic_cinema_ru

import atomic_cinema_ru.features.cinema.configureCinemaRouting
import atomic_cinema_ru.features.genre.configureGenreRouting
import atomic_cinema_ru.features.hall.configureHallRouting
import atomic_cinema_ru.features.login.configureLoginRouting
import atomic_cinema_ru.features.movie.configureMovieRouting
import atomic_cinema_ru.features.profile.configureProfileRouting
import atomic_cinema_ru.features.register.configureRegisterRouting
import atomic_cinema_ru.features.seance.configureSeanceRouting
import atomic_cinema_ru.features.security.configureSecurity
import atomic_cinema_ru.features.ticket.configureTicketRouting
import io.ktor.server.application.*
import atomic_cinema_ru.plugins.*
import atomic_cinema_ru.security.hashing.SHA256HashingService
import atomic_cinema_ru.security.token.JwtTokenService
import atomic_cinema_ru.security.token.TokenConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.postgresql.core.Oid.JSON

fun main(args : Array<String>) : Unit = io.ktor.server.netty.EngineMain.main(args)


fun Application.module() {

    Database.connect("jdbc:postgresql://0.0.0.0:5432/postgres", driver = "org.postgresql.Driver", "postgres", "admin123")

    install(ContentNegotiation){
        json(Json{
            ignoreUnknownKeys = true
        })
    }

    val hashingService = SHA256HashingService()
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )

    configureSecurity(tokenConfig)
    configureRegisterRouting(hashingService)
    configureLoginRouting(tokenConfig, hashingService, tokenService)
    configureMovieRouting()
    configureRouting()
    configureProfileRouting()
    configureSeanceRouting()
    configureTicketRouting()
    configureGenreRouting()
    configureCinemaRouting()
    configureHallRouting()
}
