package atomic_cinema_ru.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("cinema.tokens") {
    private val id = Tokens.integer("idtoken").autoIncrement()
    private val login = Tokens.varchar("login", 50)
    private val token = Tokens.varchar("token", 50)

    fun insert(tokensDTO: TokenDTO){
        transaction {
            Tokens.insert {
                it[login] = tokensDTO.login
                it[token] = tokensDTO.token
            }
        }
    }

    fun fetchToken(tokenUser : String) : TokenDTO?{
        return try {
            transaction {
                val tokenModel = Tokens.select{token.eq(tokenUser)}.single()

                TokenDTO(
                    token = tokenModel[token],
                    login = tokenModel[login]
                )
            }
        }catch (e : Exception){
            null
        }
    }

}