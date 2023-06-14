package atomic_cinema_ru.database.genres

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Genres : Table("cinema.genre") {
    private val id = Genres.integer("idgenre")
    private val name = Genres.varchar("namegenre", 50)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun getAllGenre() : List<GenreDTO>{
        val genresDTO = mutableListOf<GenreDTO>()

        transaction {
            val genres = Genres.selectAll()

            genres.forEach { genre ->
                genresDTO.add(
                    GenreDTO(
                        id = genre[Genres.id],
                        name = genre[name]
                    )
                )
            }
        }
        return genresDTO.toList()
    }
}