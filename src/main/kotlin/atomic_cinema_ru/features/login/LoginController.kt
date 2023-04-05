package atomic_cinema_ru.features.login

import atomic_cinema_ru.database.tokens.TokenDTO
import atomic_cinema_ru.database.tokens.Tokens
import atomic_cinema_ru.database.users.Users

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*
import kotlin.math.log

class LoginController{
    suspend fun performLogin(call : ApplicationCall){
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(receive.login)

        if(userDTO == null){
            call.respond(HttpStatusCode.BadRequest, "User not found")
        }else{
            if(userDTO.password == receive.password){
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        login = receive.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))
            }else{
                call.respond(HttpStatusCode.BadRequest, "invalid password")
            }
        }
    }

    suspend fun performLoginWithToken(call : ApplicationCall){
        val receive = call.receive<TokenReceiveRemote>()
        val tokenDTO = Tokens.fetchToken(receive.token)

        if(tokenDTO == null){
            call.respond(HttpStatusCode.BadRequest, "Token is not valid")
        }else{
            val user = Users.fetchUser(tokenDTO.login)

            if(user != null){
                call.respond(TokenResponseRemote(
                    id = user.id,
                    login = user.login,
                    password = user.password,
                    dateBirth = user.dateBirth,
                    numberPhone = user.numberPhone,
                    firstName = user.firstName,
                    name = user.name,
                    lastName = user.lastName,
                    idRole = user.idRole
                ))
            }else{
                call.respond(HttpStatusCode.BadRequest, "An error occurred, please try again later")
            }
        }
    }
}