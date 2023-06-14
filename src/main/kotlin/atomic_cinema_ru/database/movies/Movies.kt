package atomic_cinema_ru.database.movies

import atomic_cinema_ru.database.movieGenres.MovieGenres
import atomic_cinema_ru.database.users.Users
import atomic_cinema_ru.utils.Values
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Movies : Table("cinema.movie") {
    private val id = Movies.integer("idmovie").autoIncrement()
    private val ageRating = Movies.char("agerating", 3)
    private val description = Movies.varchar("description", 1000)
    private val director = Movies.varchar("director", 50)
    private val duration = Movies.integer("duration")
    private val linkImage = Movies.varchar("linkImage", 100)
    private val nameMovie = Movies.varchar("namemovie", 50)
    private val yearOfIssue = Movies.char("yearofissue", 4)

    override val primaryKey = PrimaryKey(id)


    fun deleteMovie(id: Int) : Boolean = transaction {
        try{
            Movies.deleteWhere { Movies.id.eq(id) }
            true
        }catch (e : Exception){
            false
        }
    }
    fun insertMovie(movie: MovieDTO?) : Int? = transaction {
        if(movie != null){
            val result = Movies.insert {
                it[nameMovie] = movie.nameMovie
                it[ageRating] = movie.ageRating
                it[description] = movie.description
                it[linkImage] = movie.linkImage
                it[director] = movie.director
                it[duration] = movie.duration
                it[yearOfIssue] = movie.yearOfIssue
            }

            result.resultedValues!!.first()[Movies.id]
        }else{
            null
        }
    }


    fun update(movie: MovieDTO): MovieDTO? = transaction {

        try {
            Movies.update({ Movies.id eq movie.id }) {
                it[ageRating] = movie.ageRating
                it[nameMovie] = movie.nameMovie
                it[description] = movie.description
                it[Movies.duration] = movie.duration
                it[director] = movie.director
                it[yearOfIssue] = movie.yearOfIssue
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        val movieModel = MoviesView.fetchMovie(movie.id)

        if (movieModel != null) {
            MovieDTO(
                id = movieModel.id,
                nameMovie = movieModel.nameMovie,
                description = movieModel.description,
                director = movieModel.director,
                duration = movieModel.duration,
                ageRating = movieModel.ageRating,
                yearOfIssue = movieModel.yearOfIssue,
                linkImage = movieModel.linkImage
            )
        } else {
            null
        }

    }
}
