package atomic_cinema_ru.database.seances

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.transactions.transaction

object Seances : Table("cinema.seance") {
    private val id = Seances.integer("idseance").autoIncrement()
    private val dateEnd = Seances.date("dateend")
    private val dateStart = Seances.date("datestart")
    private val timeEnd = Seances.time("timeend")
    private val timeStart = Seances.time("timestart")
    private val price = Seances.decimal("price", 7, 2)
    private val idHall = Seances.integer("idhall")
    private val idMovie = Seances.integer("idmovie")

    fun insert(seancesDTO: SeancesDTO) = transaction{
        Seances.insert {
            it[dateStart] = seancesDTO.dateStart.toJavaLocalDate()
            it[dateEnd] = seancesDTO.dateEnd.toJavaLocalDate()
            it[timeStart] = seancesDTO.timeStart.toJavaLocalTime()
            it[timeEnd] = seancesDTO.timeEnd.toJavaLocalTime()
            it[price] = seancesDTO.price
            it[idHall] = seancesDTO.idHall
            it[idMovie] = seancesDTO.idMovie
        }
    }

    fun delete(id : Int) = transaction{
        Seances.deleteWhere { Seances.id.eq(id)}
    }

    fun update(seancesDTO: SeancesDTO) = transaction {
        Seances.update({Seances.id.eq(seancesDTO.id)}){
            it[dateStart] = seancesDTO.dateStart.toJavaLocalDate()
            it[dateEnd] = seancesDTO.dateEnd.toJavaLocalDate()
            it[timeStart] = seancesDTO.timeStart.toJavaLocalTime()
            it[timeEnd] = seancesDTO.timeEnd.toJavaLocalTime()
            it[price] = seancesDTO.price
            it[idHall] = seancesDTO.idHall
            it[idMovie] = seancesDTO.idMovie
        }
    }
}