package atomic_cinema_ru.database.users

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("cinema.user"){
    private val id = integer("iduser").autoIncrement()
    private val login = varchar("login", 50)
    private val password = varchar("Password", 100)
    private val salt = varchar("salt", 100)
    private val dateBirth = Users.date("datebirth")
    private val firstName = varchar("firstname", 100)
    private val name = varchar("Name", 100)
    private val lastName = varchar("lastname", 100)
    private val numberPhone = varchar("numberphone", 25)
    private val idRole = integer("idrole")
    private val balance = double("balance")

    override val primaryKey = PrimaryKey(id)

    fun insert(userDTO: UserDTO){
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[salt] = userDTO.salt
                it[dateBirth] = userDTO.dateBirth
                it[firstName] = userDTO.firstName
                it[name] = userDTO.name
                it[lastName] = userDTO.lastName
                it[numberPhone] = userDTO.numberPhone
                it[idRole] = userDTO.idRole
            }
        }
    }

    fun update(userDTO: UserDTO){
        transaction {
            Users.update({Users.id.eq(userDTO.id)}){
                it[firstName] = userDTO.firstName
                it[name] = userDTO.name
                it[lastName] = userDTO.lastName
                it[dateBirth] = userDTO.dateBirth
                it[numberPhone] = userDTO.numberPhone
            }
        }
    }

    fun updateBalanceUser(user : UserDTO){
        transaction {
            Users.update({Users.id.eq(user.id)}){
                it[balance] = user.balance
            }
        }
    }


   fun fetchUser(loginUser:String = "", userID: String = "0") : UserDTO?{
        return try {
            transaction {
                if(userID != "0"){
                    val userModel = Users.select{Users.id.eq(userID.toInt())}.single()

                    UserDTO(
                        id = userModel[Users.id],
                        login = userModel[login],
                        password = userModel[password],
                        firstName = userModel[firstName],
                        salt = userModel[salt],
                        name = userModel[name],
                        lastName = userModel[lastName],
                        dateBirth = userModel[dateBirth],
                        numberPhone = userModel[numberPhone],
                        idRole = userModel[idRole],
                        balance = userModel[balance]
                    )
                }else{
                    val userModel = Users.select{login.eq(loginUser)}.single()

                    UserDTO(
                        id = userModel[Users.id],
                        login = userModel[login],
                        password = userModel[password],
                        firstName = userModel[firstName],
                        salt = userModel[salt],
                        name = userModel[name],
                        lastName = userModel[lastName],
                        dateBirth = userModel[dateBirth],
                        numberPhone = userModel[numberPhone],
                        idRole = userModel[idRole],
                        balance = userModel[balance]
                    )
                }
            }
        }catch (e : Exception){
            null
        }
    }

}