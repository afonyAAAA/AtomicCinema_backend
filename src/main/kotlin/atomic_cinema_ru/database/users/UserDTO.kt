package atomic_cinema_ru.database.users

import java.time.LocalDate

class UserDTO (
    var id : Int = 0,
    val login : String,
    val password : String,
    val dateBirth : LocalDate,
    val firstName : String,
    val name : String,
    val lastName : String,
    val numberPhone : String,
    val idRole : Int
)
