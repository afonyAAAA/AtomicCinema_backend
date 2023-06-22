package atomic_cinema_ru.features.profile

import atomic_cinema_ru.database.tickets.TicketsUsers
import atomic_cinema_ru.database.users.UserDTO
import atomic_cinema_ru.database.users.Users
import atomic_cinema_ru.features.security.getSecretUserInfo
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.time.LocalDate

suspend fun getProfileInfo(call: ApplicationCall) {

    val userInfo = getSecretUserInfo(call)

    if(userInfo != null){
        val userDTO = Users.fetchUser(userID = userInfo.userID)

        if (userDTO != null) {
            call.respond(
                ProfileInfoResponseRemote(
                    login = userDTO.login,
                    firstName = userDTO.firstName,
                    name = userDTO.name,
                    lastName = userDTO.lastName,
                    numberPhone = userDTO.numberPhone,
                    dateOfBirth = userDTO.dateBirth,
                    balance = userDTO.balance.toString()
                )
            )
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }else{
        call.respond(HttpStatusCode.Unauthorized)
    }
}

suspend fun updateBalance(call : ApplicationCall){
    val receive = call.receive<ProfileBalanceReceiveRemote>()

    val principal = call.principal<JWTPrincipal>()
    val userID = principal?.getClaim("userId", String::class)

    Users.updateBalanceUser(
        UserDTO(
            id = userID.toString().toInt(),
            balance = receive.balance.toDouble()
        )
    )

    call.respond(HttpStatusCode.OK)
}

suspend fun getPaymentsForMonth(call : ApplicationCall){

    val principal = call.principal<JWTPrincipal>()
    val userID = principal?.getClaim("userId", String::class)
        ?: call.respond(HttpStatusCode.Unauthorized) as String

    val result = TicketsUsers.getAllTicketsUser(userID = userID.toInt())

    val presentTime = LocalDate.now()

    if(result.isNotEmpty()){
        val sumPayForMonth = result.filter {
            it!!.dateTime.monthValue == presentTime.monthValue &&
            it.dateTime.year == presentTime.year &&
            !it.returned &&
            it.nameStatus == "Оплачено"
        }.sumOf { it!!.price * it.count }

        val  sumPurchasedTickets = result.filter {
            it!!.dateTime.monthValue == presentTime.monthValue &&
                    it.dateTime.year == presentTime.year &&
                    !it.returned &&
                    it.nameStatus == "Оплачено"
        }.sumOf { it!!.count }

        if(sumPayForMonth == 0.0 || sumPurchasedTickets == 0){
            call.respond(HttpStatusCode.NotFound)
        }else{
            call.respond(
                ProfileInfoPaymentsResponse(
                    sumPay = sumPayForMonth,
                    countTickets = sumPurchasedTickets
                )
            )
        }
    }else{
        call.respond(HttpStatusCode.NotFound)
    }
}

suspend fun editProfile(call : ApplicationCall){
    val userInfo = getSecretUserInfo(call)
    val receive = call.receive<ProfileEditReceiveRemote>()

    if(userInfo != null){
        Users.update(UserDTO(
            id = userInfo.userID.toInt(),
            firstName = receive.firstName,
            name = receive.name,
            lastName = receive.lastName,
            numberPhone = receive.numberPhone,
            dateBirth = receive.dateOfBirth
        ))

        call.respond(HttpStatusCode.OK)
    }else{
        call.respond(HttpStatusCode.Unauthorized)
    }
}



