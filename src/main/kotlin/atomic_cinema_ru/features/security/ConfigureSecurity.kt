package atomic_cinema_ru.features.security

import atomic_cinema_ru.database.tokens.RevListToken
import atomic_cinema_ru.features.login.SecretInfoResponseRemote
import atomic_cinema_ru.security.token.TokenConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity(config : TokenConfig){
    authentication {
        jwt {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build())

            validate { credential ->
               if(credential.payload.audience.contains(config.audience)){
                   JWTPrincipal(credential.payload)
               }else null
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}



fun Route.authenticate(){
    authenticate {
        get("authenticate"){
            val receive = call.request.header("Authorization")?.
            removePrefix("Bearer ")

           if(receive != null){
               val badToken = RevListToken.fetchToken(tokenUser = receive)

               if(badToken.isNullOrBlank()){
                   val principal = call.principal<JWTPrincipal>()
                   val role = principal?.getClaim("role", String::class) ?: call.respond(HttpStatusCode.NotFound)
                   val userID = principal?.getClaim("userId", String::class)?: call.respond(HttpStatusCode.NotFound)

                   call.respond(SecretInfoResponseRemote(
                       id = userID.toString().toInt(),
                       role = role.toString()
                   ))
               }else
                   call.respond(HttpStatusCode.Unauthorized)
           }else{
               call.respond(HttpStatusCode.Unauthorized)
           }
        }
    }
}

suspend fun getSecretUserInfo(call : ApplicationCall) : SecretInfoUser?{
    val principal = call.principal<JWTPrincipal>()
    val userID = principal?.getClaim("userId", String::class)
    val role = principal?.getClaim("role", String::class)

    return if(userID != null && role != null){
        SecretInfoUser(
            userID = userID,
            role = role
        )
    }else{
        null
    }
}

data class SecretInfoUser(
    val userID : String,
    val role : String,
)