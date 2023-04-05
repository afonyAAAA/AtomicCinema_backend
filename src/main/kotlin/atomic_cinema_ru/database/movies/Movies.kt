package atomic_cinema_ru.database.movies

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Movies : Table("view_holder.showallmovie") {
    private val id = Movies.integer("idmovie").autoIncrement()
    private val ageRating = Movies.char("agerating", 3)
    private val description = Movies.varchar("description", 1000)
    private val director = Movies.varchar("director", 50)
    private val duration = Movies.integer("duration")
    private val linkImage = Movies.varchar("linkimage", 50)
    private val nameMovie = Movies.varchar("namemovie", 50)
    private val yearOfIssue = Movies.char("yearofissue", 4)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun getAllMovie() : MutableList<MovieDTO>{
        val movies : MutableList<MovieDTO> = mutableListOf()

        transaction {
            Movies.selectAll().forEach{ movie ->
                   val movie = MovieDTO(
                        id = movie[Movies.id],
                        ageRating = movie[ageRating],
                        description = movie[description],
                        nameMovie = movie[nameMovie],
                        duration = movie[Movies.duration],
                        director = movie[director],
                        yearOfIssue = movie[yearOfIssue])
                movies.add(movie)
            }
        }
        return movies
    }
}