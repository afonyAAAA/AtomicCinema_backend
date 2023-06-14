package atomic_cinema_ru.features.genre

import atomic_cinema_ru.database.genres.Genres
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun getAllGenre(call : ApplicationCall){

    val genresResponse : MutableList<GenreResponse> = mutableListOf()

    Genres.getAllGenre().forEach {
        genresResponse.add(GenreResponse(nameGenre = it.name, id = it.id))
    }

    call.respond(genresResponse)
}