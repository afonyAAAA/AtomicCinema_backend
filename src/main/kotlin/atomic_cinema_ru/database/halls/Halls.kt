package atomic_cinema_ru.database.halls

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Halls : Table("cinema.showcinemawithhall") {
    private val idCinema = integer("idcinema")
    private val idHall = integer("idhall")
    private val nameTypeHall = varchar("nametype", 50)
    private val capacity = varchar("capacity", 3)


    fun getHallsCinema(idCinema : Int) : MutableList<HallsDTO> = transaction{
        val listHalls : MutableList<HallsDTO> = mutableListOf()

        val result = Halls.select { Halls.idCinema.eq(idCinema) }

        result.forEach { hall ->
            listHalls.add(
                HallsDTO(
                    idCinema = hall[Halls.idCinema],
                    idHall = hall[idHall],
                    capacity = hall[capacity],
                    nameTypeHall = hall[nameTypeHall]
                )
            )
        }

        listHalls


    }
}
