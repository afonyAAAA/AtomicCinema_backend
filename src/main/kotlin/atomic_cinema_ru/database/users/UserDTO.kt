package atomic_cinema_ru.database.users

import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

class UserDTO (
    val id : Int,
    val login : String,
    val password : String,
    val dateBirth : LocalDate,
    val firstName : String,
    val name : String,
    val lastName : String,
    val numberPhone : String,
)