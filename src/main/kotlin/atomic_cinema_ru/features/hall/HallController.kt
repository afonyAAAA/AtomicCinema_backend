package atomic_cinema_ru.features.hall

import atomic_cinema_ru.database.halls.Halls
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun getHallsCinema(call : ApplicationCall){
    val receive = call.receive<HallReceive>()

    val result = Halls.getHallsCinema(receive.idCinema)

    if(result.isNotEmpty()){
        val hallsResponse = mutableListOf<HallResponse>()

        result.forEach{ result ->
            hallsResponse.add(
                HallResponse(
                    idHall = result.idHall,
                    capacity = result.capacity,
                    nameTypeHall = result.nameTypeHall
                )
            )
        }

        call.respond(hallsResponse)

    }else{
        call.respond(HttpStatusCode.BadRequest)
    }
}