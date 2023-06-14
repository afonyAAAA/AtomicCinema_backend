package atomic_cinema_ru.features.register

import atomic_cinema_ru.database.users.UserDTO
import atomic_cinema_ru.database.users.Users
import atomic_cinema_ru.security.hashing.HashingService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun registerNewUser(call : ApplicationCall, hashingService: HashingService){
    try{
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        val userDTO = Users.fetchUser(registerReceiveRemote.login)
        if(userDTO != null){
            call.respond(HttpStatusCode.Conflict, "user is already exist")
        }else{

            val saltedHash = hashingService.generateSaltedHash(registerReceiveRemote.password)

            Users.insert(
                UserDTO(
                    login = registerReceiveRemote.login,
                    password = saltedHash.hash,
                    salt = saltedHash.salt,
                    numberPhone = registerReceiveRemote.numberPhone,
                    firstName = registerReceiveRemote.firstName,
                    name = registerReceiveRemote.name,
                    lastName = registerReceiveRemote.lastName,
                    dateBirth = registerReceiveRemote.dateBirth,
                    idRole = registerReceiveRemote.idRole
                )
            )

            call.respond(HttpStatusCode.OK)

        }
    }catch (e : Exception){
        e.printStackTrace()
    }
}

