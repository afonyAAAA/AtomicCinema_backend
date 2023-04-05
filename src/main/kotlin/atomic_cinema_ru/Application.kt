package atomic_cinema_ru

import atomic_cinema_ru.features.login.configureLoginRouting
import atomic_cinema_ru.features.movie.configureMovieRouting
import atomic_cinema_ru.features.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import atomic_cinema_ru.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql.Driver", "postgres", "admin123")

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureMovieRouting()
    configureRegisterRouting()
    configureLoginRouting()
    configureRouting()
}
