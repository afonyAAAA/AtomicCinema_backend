package atomic_cinema_ru.features.register

import atomic_cinema_ru.database.tokens.Tokens
import atomic_cinema_ru.database.tokens.TokenDTO
import atomic_cinema_ru.database.users.UserDTO
import atomic_cinema_ru.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*


class RegisterController{

    suspend fun registerNewUser(call : ApplicationCall){

        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()

        val userDTO = Users.fetchUser(registerReceiveRemote.login)

        if(userDTO != null){
            call.respond(HttpStatusCode.Conflict, "user is already exist")
        }else{
            val token = UUID.randomUUID().toString()

                Users.insert(
                    UserDTO(
                        login = registerReceiveRemote.login,
                        password = registerReceiveRemote.password,
                        numberPhone = registerReceiveRemote.numberPhone,
                        firstName = registerReceiveRemote.firstName,
                        name = registerReceiveRemote.name,
                        lastName = registerReceiveRemote.lastName,
                        dateBirth = registerReceiveRemote.dateBirth,
                        idRole = registerReceiveRemote.idRole
                    )
                )
                Tokens.insert(TokenDTO(token = token, login = registerReceiveRemote.login))

            call.respond(RegisterResponseRemote(token = token))
        }


    }
}
