package atomic_cinema_ru.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object RevListToken : Table("cinema.revListToken") {
    private val token = RevListToken.varchar("token", 250)
    fun insert(tokenUser: String){
       try {
            transaction {
                RevListToken.insert {
                    it[token] = tokenUser
                }
            }
       }catch (e : Exception){
           e.printStackTrace()
       }
    }

    fun fetchToken(tokenUser : String) : String?{
        return try {
            transaction {
                val tokenModel = RevListToken.select{token.eq(tokenUser)}.single()
                tokenModel.toString()
            }
        }catch (e : Exception){
            null
        }
    }

}