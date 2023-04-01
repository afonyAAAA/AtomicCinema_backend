package atomic_cinema_ru.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("User") {
    private val id = Users.integer("iduser")
    private val login = Users.varchar("login", 50)
    private val password = Users.varchar("Password", 50)
    private val dateBirth = Users.date("datebirth")
    private val firstName = Users.varchar("firstname", 100)
    private val name = Users.varchar("Name", 100)
    private val lastName = Users.varchar("lastName", 100)
    private val numberPhone = Users.varchar("numberPhone", 25)

    fun insert(userDTO: UserDTO){
        transaction {
            Users.insert {
                it[id] = userDTO.id
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[dateBirth] = userDTO.dateBirth
                it[firstName] = userDTO.firstName
                it[name] = userDTO.name
                it[lastName] = userDTO.lastName
                it[numberPhone] = userDTO.numberPhone
            }
        }
    }

    fun fetchUser(loginUser:String) : UserDTO{
        val userModel = Users.select{Users.login.eq(login)}.single()
        return UserDTO(
            id = userModel[id],
            login = userModel[login],
            password = userModel[password],
            firstName = userModel[firstName],
            name = userModel[name],
            lastName = userModel[lastName],
            dateBirth = userModel[dateBirth],
            numberPhone = userModel[numberPhone]
        )
    }

}