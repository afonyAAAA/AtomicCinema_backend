package atomic_cinema_ru.features.login

import atomic_cinema_ru.database.tokens.RevListToken
import atomic_cinema_ru.database.users.Users
import atomic_cinema_ru.security.hashing.HashingService
import atomic_cinema_ru.security.hashing.SaltedHash
import atomic_cinema_ru.security.token.TokenClaim
import atomic_cinema_ru.security.token.TokenConfig
import atomic_cinema_ru.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun performLogin(
    call : ApplicationCall,
    hashingService: HashingService,
    tokenService : TokenService,
    tokenConfig: TokenConfig) {

    try {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(receive.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.NotFound, "User not found")
        } else {

            val isValidPassword = hashingService.verify(
                value = receive.password,
                saltedHash = SaltedHash(
                    hash = userDTO.password,
                    salt = userDTO.salt
                )
            )

            if (!isValidPassword) {
                call.respond(HttpStatusCode.BadRequest, "Password not valid")
            } else {
                val token = tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = userDTO.id.toString()
                    ),
                    TokenClaim(
                        name = "role",
                        value = when(userDTO.idRole){
                            1 -> "customer"
                            2 -> "employee"
                            3 -> "admin"
                            else -> ""
                        }
                    )
                )
                call.respond(
                    status = HttpStatusCode.OK,
                    message = LoginResponseRemote(
                        token = token
                    )
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

suspend fun deactivateToken(call : ApplicationCall){
    val receive = call.request.header("Authorization")?.
    removePrefix("Bearer ")

    if (receive != null) {
        RevListToken.insert(receive)
    }

    call.respond(HttpStatusCode.OK)
}



