package atomic_cinema_ru.database.numbersPhoneCinema

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object NumbersPhoneCinema : Table("cinema.cinemanumbersphone") {
    private val idcinema = integer("idcinema")
    private val number = varchar("numberphone", 25)



    fun getAllNumbersPhone() : List<CinemaNumbersPhoneDTO?>{

        val listNumbers = mutableListOf<CinemaNumbersPhoneDTO>()

        return transaction {
            val numbers = NumbersPhoneCinema.selectAll().orderBy(idcinema)

            numbers.forEach { numbers ->
                listNumbers.add(
                    CinemaNumbersPhoneDTO(
                        cinemaID = numbers[idcinema],
                        numberPhone = numbers[number]
                    ))
            }

            listNumbers.toList()
        }
    }
}