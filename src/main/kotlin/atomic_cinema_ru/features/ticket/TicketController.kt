package atomic_cinema_ru.features.ticket

import atomic_cinema_ru.database.tickets.TicketDTO
import atomic_cinema_ru.database.tickets.Tickets
import atomic_cinema_ru.database.tickets.TicketsUsers
import atomic_cinema_ru.features.register.LocalTimeSerializer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.time.LocalDate
import java.time.LocalTime

suspend fun createTicket(call : ApplicationCall){

    val receive = call.receive<TicketReceiveCreateTicket>()

    Tickets.insert(
        TicketDTO(
            count = receive.count,
            idSeance = receive.idSeance,
            idStatusPayment = receive.idStatusPayment,
            idUser = receive.idUser,
            dateTime = receive.dateTime,
            dateStartSeance = receive.dateStartSeance
        )
    )

    call.respond(HttpStatusCode.OK)
}

suspend fun updateStatusTicket(call: ApplicationCall){
    val receive = call.receive<TicketReceiveUpdateTicket>()

    val result = Tickets.update(
        receive.idTicket,
        receive.idStatusPayment,
        receive.returned
    )

    if(result){
        call.respond(HttpStatusCode.OK)
    }else{
        call.respond(HttpStatusCode.BadRequest)
    }
}

suspend fun checkPresentsTickets(call: ApplicationCall){
    val receive = call.receive<TicketsUserReceiveRemote>()

    val presentsTime = LocalDate.now()

    val result = getTicketsUserByLogin(receive.login).filter { it.dateStartSeance == presentsTime && !it.returned && it.nameStatus == "Оплачено" }

    if(result.isNotEmpty()){
        call.respond(result)
    }else{
        call.respond(HttpStatusCode.NotFound)
    }
}

suspend fun checkTicketsOnSeances(call: ApplicationCall){
    val principal = call.principal<JWTPrincipal>()
    val userID = principal?.getClaim("userId", String::class)
        ?: call.respond(HttpStatusCode.Unauthorized) as String

    val presentsTime = LocalDate.now()

    val result = TicketsUsers.getAllTicketsUser(userID = userID.toInt()).filter {
        it.dateStartSeance == presentsTime &&
                !it.returned &&
                it.nameStatus == "Оплачено"
    }

    if(result.isNotEmpty()){
        call.respond(HttpStatusCode.OK)
    }else{
        call.respond(HttpStatusCode.NotFound)
    }

}

fun getTicketsUserByLogin(loginUser : String) : List<TicketResponseRemote>{

    val result = TicketsUsers.getAllTicketsUser(loginUser)

    val listTickets = result.map{
        TicketResponseRemote(
            id = it.id,
            idSeance = it.idSeance,
            idUser = it.idUser,
            nameStatus = it.nameStatus,
            nameTypeHall = it.nameTypeHall,
            nameMovie = it.nameMovie,
            count = it.count,
            addressCinema = it.addressCinema,
            price = it.price,
            timeStart = it.timeStart,
            timeEnd = it.timeEnd,
            ageRating = it.ageRating,
            duration = it.duration,
            linkImage = it.linkImage,
            returned = it.returned,
            dateTime = it.dateTime,
            dateStartSeance = it.dateStartSeance
        )
    }
    return listTickets
}

suspend fun getTicketsUsers(call: ApplicationCall){
    val principal = call.principal<JWTPrincipal>()
    val userID = principal?.getClaim("userId", String::class)
        ?: call.respond(HttpStatusCode.Unauthorized) as String

    val result = TicketsUsers.getAllTicketsUser(userID = userID.toInt())

    val responseList : List<TicketResponseRemote?> = result.map{
        if(it != null){
            TicketResponseRemote(
                id = it.id,
                idUser = it.idUser,
                nameStatus = it.nameStatus,
                nameTypeHall = it.nameTypeHall,
                nameMovie = it.nameMovie,
                count = it.count,
                addressCinema = it.addressCinema,
                price = it.price,
                timeStart = it.timeStart,
                timeEnd = it.timeEnd,
                ageRating = it.ageRating,
                duration = it.duration,
                linkImage = it.linkImage,
                returned = it.returned,
                dateTime = it.dateTime,
                dateStartSeance = it.dateStartSeance
            )
        }else{
            null
        }
    }

    if(responseList.isNotEmpty()){
        call.respond(responseList.sortedBy { it!!.dateTime })
    }else{
        call.respond(HttpStatusCode.NotFound)
    }
}