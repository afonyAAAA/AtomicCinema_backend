package atomic_cinema_ru.database.movieGenres

import atomic_cinema_ru.database.movies.MoviesView
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object MovieGenres : Table("cinema.moviegenres"){
    private val idGenre = integer("idgenre")
    private val idMovie = integer("idmovie")

    fun insert(idMovie : Int, listIdGenres : List<Int>) : Boolean = transaction {
        try{
            MovieGenres.batchInsert(listIdGenres) { id ->
                this[MovieGenres.idMovie] = idMovie
                this[idGenre] = id
            }
        }catch (e : Exception){
            false
        }
        true
    }


    fun deleteAllGenresMovie(id : Int) : Boolean = transaction {
        try {
            MovieGenres.deleteWhere { idMovie.eq(id) }
            true
        }catch (e : Exception){
            false
        }
    }
}