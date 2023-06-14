package atomic_cinema_ru.features.cinema

import atomic_cinema_ru.database.cinemas.Cinemas
import atomic_cinema_ru.database.tickets.Tickets
import atomic_cinema_ru.database.tickets.TicketsUsers
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun getAllCinema(call : ApplicationCall){


    val cinemas = Cinemas.getAllCinema()
    val cinemasResponse : MutableList<CinemaResponseRemote> = mutableListOf()

    if(!cinemas.isEmpty()){
        cinemas.sortedBy {it.addressCinema.substringBefore(",")}.forEach { cinema ->
            cinemasResponse.add(
                CinemaResponseRemote(
                    id = cinema.id,
                    addressCinema = cinema.addressCinema,
                    numbersPhone = cinema.numbersPhone.map { it!!.numberPhone }
                )
            )
        }
    }else{
        call.respond(HttpStatusCode.BadRequest)
    }

    call.respond(cinemasResponse)
}

suspend fun formRevenueReport(call : ApplicationCall){
    val result = TicketsUsers.getDetailsRevenueReport()

    var sumMoney = 0.0

    result.filter { it.nameStatus == "Оплачено" }.forEach { ticket ->
        sumMoney += ticket.price * ticket.count
    }

    if(sumMoney == 0.0){
        call.respond(HttpStatusCode.NotFound)
    }else{
        val response = RevenueReportResponseRemote(
            sumMoney = sumMoney.toString(),
            sumTickets = result.sumOf { it.count }.toString()
        )
        call.respond(response)
    }

}