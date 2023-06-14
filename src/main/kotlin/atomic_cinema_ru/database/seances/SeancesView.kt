package atomic_cinema_ru.database.seances

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object SeancesView : Table("cinema.showallseance") {
    private val id = SeancesView.integer("idseance").autoIncrement()
    private val dateEnd = SeancesView.date("dateend")
    private val dateStart = SeancesView.date("datestart")
    private val timeEnd = SeancesView.time("timeend")
    private val timeStart = SeancesView.time("timestart")
    private val nameTypeHall = SeancesView.varchar("nametype",50)
    private val addressCinema = SeancesView.text("fulladdres")
    private val price = SeancesView.decimal("price", 7, 2)
    private val idHall = SeancesView.integer("idhall")
    private val idMovie = SeancesView.integer("idmovie")


    fun getSeanceMovie(id: Int): MutableList<SeancesDTO?> {

        val seances = mutableListOf<SeancesDTO?>()

        transaction {
            SeancesView.select(idMovie.eq(id)).sortedByDescending { dateStart }.forEach { item ->

                val seance = SeancesDTO(
                    id = item[SeancesView.id],
                    dateEnd = item[dateEnd].toKotlinLocalDate(),
                    dateStart = item[dateStart].toKotlinLocalDate(),
                    timeEnd = item[timeEnd].toKotlinLocalTime(),
                    timeStart = item[timeStart].toKotlinLocalTime(),
                    idMovie = item[idMovie],
                    idHall = item[idHall],
                    price = item[price],
                    nameTypeHall = item[nameTypeHall],
                    addressCinema = item[addressCinema]
                )
                seances.add(seance)
            }
        }

        return seances
    }

    fun getSeancesCinema(addressCinema : String) : List<SeancesDTO>{
        return transaction{
            val result = SeancesView.select(SeancesView.addressCinema.eq(addressCinema))

            val presentTime = LocalDate.now()

            val listSeances = result.map { seance ->
                SeancesDTO(
                    id = seance[SeancesView.id],
                    dateEnd = seance[dateEnd].toKotlinLocalDate(),
                    dateStart = seance[dateStart].toKotlinLocalDate(),
                    timeEnd = seance[timeEnd].toKotlinLocalTime(),
                    timeStart = seance[timeStart].toKotlinLocalTime(),
                    idMovie = seance[idMovie],
                    idHall = seance[idHall],
                    price = seance[price],
                    nameTypeHall = seance[nameTypeHall],
                    addressCinema = seance[SeancesView.addressCinema]
                )
            }.toMutableList()

            listSeances
        }
    }

    fun getSeancesByIdHall(idHall : Int) : List<SeancesDTO> = transaction {
        val seances = SeancesView.select {SeancesView.idHall.eq(idHall) }

        val listSeancesDTO = mutableListOf<SeancesDTO>()

        seances.forEach{ seance ->
            listSeancesDTO.add(
                SeancesDTO(
                    id = seance[SeancesView.id],
                    dateEnd = seance[dateEnd].toKotlinLocalDate(),
                    dateStart = seance[dateStart].toKotlinLocalDate(),
                    timeEnd = seance[timeEnd].toKotlinLocalTime(),
                    timeStart = seance[timeStart].toKotlinLocalTime(),
                    idMovie = seance[idMovie],
                    idHall = seance[SeancesView.idHall],
                    price = seance[price],
                    nameTypeHall = seance[nameTypeHall],
                    addressCinema = seance[addressCinema]
                )
            )
        }
        listSeancesDTO
    }

}