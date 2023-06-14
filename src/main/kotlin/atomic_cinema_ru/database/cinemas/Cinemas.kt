package atomic_cinema_ru.database.cinemas

import atomic_cinema_ru.database.numbersPhoneCinema.NumbersPhoneCinema
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Cinemas : Table("cinema.showallcinema") {
    private val id = integer("idcinema")
    private val addressCinema = text("fulladdres")



    fun getAllCinema() : List<CinemasDTO>{

        val listCinema = mutableListOf<CinemasDTO>()

        return transaction {
            val cinemas = Cinemas.selectAll()
            val numbersPhone = NumbersPhoneCinema.getAllNumbersPhone()

            cinemas.forEach { cinema ->
                listCinema.add(CinemasDTO(
                    id = cinema[Cinemas.id],
                    addressCinema = cinema[addressCinema],
                    numbersPhone = numbersPhone.filter { it?.cinemaID == cinema[Cinemas.id]}
                ))
            }

           listCinema
        }
    }



}