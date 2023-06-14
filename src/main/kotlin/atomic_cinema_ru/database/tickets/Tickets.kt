package atomic_cinema_ru.database.tickets

import atomic_cinema_ru.database.tickets.Tickets.autoIncrement
import atomic_cinema_ru.database.tickets.Tickets.id
import atomic_cinema_ru.database.tickets.Tickets.update
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.month
import org.jetbrains.exposed.sql.javatime.year
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime

object Tickets : Table("cinema.ticket") {
    private val id = Tickets.integer("idticket").autoIncrement()
    private val count = Tickets.integer("Count")
    private val dateTime = Tickets.datetime("datetime")
    private val returned = Tickets.bool("returned")
    private val idUser = Tickets.integer("iduser")
    private val idStatusPayment = Tickets.integer("idstatuspayment")
    private val idSeance = Tickets.integer("idseance")
    private val dateStartSeance = Tickets.date("datestartseance")


    fun insert(ticket: TicketDTO){
        transaction {
            Tickets.insert {
                it[count] = ticket.count
                it[dateTime] = ticket.dateTime
                it[returned] = ticket.returned
                it[idUser] = ticket.idUser
                it[idStatusPayment] = ticket.idStatusPayment
                it[idSeance] = ticket.idSeance
                it[dateStartSeance] = ticket.dateStartSeance
            }
        }
    }

    fun update(
        idTicket : Int,
        idStatusPayment : Int = 0,
        returned : Boolean? = null
    ) : Boolean{
        return transaction {
            if(idStatusPayment == 0 && returned != null){
                Tickets.update ({ Tickets.id.eq(idTicket)}){
                    it[Tickets.returned] = returned
                }
                true

            }else if (idStatusPayment != 0 && returned == null){
                Tickets.update ({ Tickets.id.eq(idTicket)}){
                    it[Tickets.idStatusPayment] = idStatusPayment
                }
                true
            }
            else{
                false
            }
        }
    }


    fun getTicketsUser(userID : Int) : List<TicketDTO?>{
        val ticketsDTO = mutableListOf<TicketDTO>()

        transaction {
            val tickets = Tickets.select { idUser.eq(userID) }

            tickets.forEach { ticket ->
                ticketsDTO.add(TicketDTO(
                    id = ticket[Tickets.id],
                    count = ticket[count],
                    dateTime = ticket[dateTime],
                    returned = ticket[returned],
                    idUser = ticket[idUser],
                    idStatusPayment = ticket[idStatusPayment],
                    idSeance = ticket[idSeance],
                    dateStartSeance = ticket[dateStartSeance]
                ))
            }
        }
       return ticketsDTO.toList()
    }
}
