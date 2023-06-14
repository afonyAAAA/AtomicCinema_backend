package atomic_cinema_ru.database.movies

import atomic_cinema_ru.database.movieGenres.MovieGenres
import atomic_cinema_ru.utils.Values
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object MoviesView : Table("cinema.showallmovie") {
    private val id = MoviesView.integer("idmovie").autoIncrement()
    private val ageRating = MoviesView.char("agerating", 3)
    private val description = MoviesView.varchar("description", 1000)
    private val director = MoviesView.varchar("director", 50)
    private val duration = MoviesView.integer("duration")
    private val linkImage = MoviesView.varchar("linkImage", 100)
    private val nameMovie = MoviesView.varchar("namemovie", 50)
    private val yearOfIssue = MoviesView.char("yearofissue", 4)
    private val genres = MoviesView.text("showgenres")

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun getAllMovie(): MutableList<MovieDTO> {
        val movies: MutableList<MovieDTO> = mutableListOf()

        transaction {
            MoviesView.selectAll().forEach { movieItem ->

                var genres = movieItem[genres]
                val listGenre = mutableListOf<String?>()
                val lastGenre = genres.substringAfterLast(", ")

                while (genres.isNotBlank()){
                    val genre = genres.substringBefore(", ")
                    genres = genres.substringAfter(", ")

                    if(genre == genres){
                        listGenre.add(lastGenre)
                        genres = ""
                    }else{
                        listGenre.add(genre)
                    }
                }

                val movie = MovieDTO(
                    id = movieItem[MoviesView.id],
                    ageRating = movieItem[ageRating],
                    description = movieItem[description],
                    nameMovie = movieItem[nameMovie],
                    duration = movieItem[MoviesView.duration],
                    director = movieItem[director],
                    linkImage = movieItem[linkImage],
                    yearOfIssue = movieItem[yearOfIssue],
                    genresMovie = listGenre
                )
                movies.add(movie)
            }
        }
        return movies
    }

    fun fetchMovie(id : Int) : MovieDTO?{
        return try {
            transaction {
                val movieModel = MoviesView.select{ MoviesView.id.eq(id)}.single()
                MovieDTO(
                    id = movieModel[MoviesView.id],
                    nameMovie = movieModel[nameMovie],
                    description = movieModel[description],
                    director = movieModel[director],
                    duration = movieModel[MoviesView.duration],
                    ageRating = movieModel[ageRating],
                    yearOfIssue = movieModel[yearOfIssue],
                    linkImage = movieModel[linkImage]
                )
            }
        }catch (e : Exception){
            null
        }
    }
}
