package atomic_cinema_ru.database.tickets

import atomic_cinema_ru.database.users.Users
import kotlinx.coroutines.channels.ticker
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.javatime.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object TicketsUsers : Table("cinema.ticketsUser") {
    private val id = integer("idticket").autoIncrement()
    private val idUser = integer("iduser")
    private val idSeance = integer("idseance")
    private val nameStatus = varchar("namestatus", 50)
    private val count = integer("Count")
    private val dateTime = datetime("datetime")
    private val returned = bool("returned")
    private val addressCinema = text("addrescinema")
    private val nameMovie = varchar("namemovie", 50)
    private val linkImage = varchar("linkImage", 100)
    private val duration = integer("duration")
    private val ageRating = varchar("agerating", 3)
    private val timeStart = time("timestart")
    private val timeEnd = time("timeend")
    private val price = double("price")
    private val nameTypeHall = varchar("nametype", 50)
    private val dateStartSeance = date("datestartseance")


    fun getAllTicketsUser(userID : Int) : List<TicketsUsersDTO>{

        val tickets = mutableListOf<TicketsUsersDTO>()

        return transaction {
            val ticketsUsers = TicketsUsers.select { idUser.eq(userID) }

            if(!ticketsUsers.empty()){
                ticketsUsers.forEach { ticket ->
                    tickets.add(TicketsUsersDTO(
                        id = ticket[TicketsUsers.id],
                        idUser = ticket[idUser],
                        nameStatus = ticket[nameStatus],
                        nameMovie = ticket[nameMovie],
                        nameTypeHall = ticket[nameTypeHall],
                        count = ticket[count],
                        dateTime = ticket[dateTime],
                        returned = ticket[returned],
                        addressCinema = ticket[addressCinema],
                        linkImage = ticket[linkImage],
                        duration = ticket[TicketsUsers.duration],
                        ageRating = ticket[ageRating],
                        timeEnd = ticket[timeEnd],
                        timeStart = ticket[timeStart],
                        price = ticket[price],
                        dateStartSeance = ticket[dateStartSeance]
                    ))
                }

            }
            tickets
        }
    }

    fun getAllTicketsUser(login : String) : List<TicketsUsersDTO>{

        val user = Users.fetchUser(loginUser = login)

        return transaction {
            val ticketsUsers = TicketsUsers.select { idUser.eq(user!!.id) }

            ticketsUsers.map { ticket ->
                TicketsUsersDTO(
                    id = ticket[TicketsUsers.id],
                    idSeance = ticket[idSeance],
                    idUser = ticket[idUser],
                    nameStatus = ticket[nameStatus],
                    nameMovie = ticket[nameMovie],
                    nameTypeHall = ticket[nameTypeHall],
                    count = ticket[count],
                    dateTime = ticket[dateTime],
                    returned = ticket[returned],
                    addressCinema = ticket[addressCinema],
                    linkImage = ticket[linkImage],
                    duration = ticket[TicketsUsers.duration],
                    ageRating = ticket[ageRating],
                    timeEnd = ticket[timeEnd],
                    timeStart = ticket[timeStart],
                    price = ticket[price],
                    dateStartSeance = ticket[dateStartSeance]
                )
            }
        }
    }


    fun getDetailsRevenueReport() : List<TicketsUsersDTO>{
        val tickets = mutableListOf<TicketsUsersDTO>()

        val presentDateTime = LocalDateTime.now()

        return transaction{
            val result = TicketsUsers.select {
                dateTime.month().eq(presentDateTime.monthValue).and {
                    dateTime.year().eq(presentDateTime.year).and {
                        returned.eq(false)
                    }
                }
            }
            result.forEach { ticket ->
                tickets.add(TicketsUsersDTO(
                    id = ticket[TicketsUsers.id],
                    idUser = ticket[idUser],
                    nameStatus = ticket[nameStatus],
                    nameMovie = ticket[nameMovie],
                    nameTypeHall = ticket[nameTypeHall],
                    count = ticket[count],
                    dateTime = ticket[dateTime],
                    returned = ticket[returned],
                    addressCinema = ticket[addressCinema],
                    linkImage = ticket[linkImage],
                    duration = ticket[TicketsUsers.duration],
                    ageRating = ticket[ageRating],
                    timeEnd = ticket[timeEnd],
                    timeStart = ticket[timeStart],
                    price = ticket[price],
                    dateStartSeance = ticket[dateStartSeance]
                ))
            }
            tickets
        }
    }

}