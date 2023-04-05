package atomic_cinema_ru.database.users

import atomic_cinema_ru.database.roles.Roles
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Table.PrimaryKey
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("cinema.user"){
    private val id = integer("iduser").autoIncrement()
    private val login = varchar("login", 50)
    private val password = varchar("Password", 50)
    private val dateBirth = Users.date("datebirth")
    private val firstName = varchar("firstname", 100)
    private val name = varchar("Name", 100)
    private val lastName = varchar("lastname", 100)
    private val numberPhone = varchar("numberphone", 25)
    private val idRole = integer("idrole")

    override val primaryKey = PrimaryKey(id)

    fun insert(userDTO: UserDTO){
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[dateBirth] = userDTO.dateBirth
                it[firstName] = userDTO.firstName
                it[name] = userDTO.name
                it[lastName] = userDTO.lastName
                it[numberPhone] = userDTO.numberPhone
                it[idRole] = userDTO.idRole
            }
        }
    }

    fun fetchUser(loginUser:String) : UserDTO?{
        return try {
            transaction {
                val userModel = Users.select{login.eq(loginUser)}.single()

                UserDTO(
                    id = userModel[Users.id],
                    login = userModel[login],
                    password = userModel[password],
                    firstName = userModel[firstName],
                    name = userModel[name],
                    lastName = userModel[lastName],
                    dateBirth = userModel[dateBirth],
                    numberPhone = userModel[numberPhone],
                    idRole = userModel[idRole]
                )
            }
        }catch (e : Exception){
            null
        }
    }

}