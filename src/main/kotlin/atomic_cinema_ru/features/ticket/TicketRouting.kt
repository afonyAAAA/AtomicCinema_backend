package atomic_cinema_ru.features.ticket


import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureTicketRouting(){
    routing {
        authenticate {
            get ("/ticketUser") {
                getTicketsUsers(call)
            }
            get("/checkTicketsOnSeances"){
                checkTicketsOnSeances(call)
            }
            post("/createTicket") {
                createTicket(call)
            }
            post("/updateTicket") {
                updateStatusTicket(call)
            }
            post("/checkPresentsTickets"){
                checkPresentsTickets(call)
            }
        }
    }
}