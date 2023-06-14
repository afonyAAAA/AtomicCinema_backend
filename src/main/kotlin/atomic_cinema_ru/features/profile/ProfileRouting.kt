package atomic_cinema_ru.features.profile

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureProfileRouting() {
    routing {
        authenticate{
            get("/profile") {
                getProfileInfo(call)
            }
            post("profile/edit"){
                editProfile(call)
            }
            post("/updateBalance"){
                updateBalance(call)
            }
            get("/getPaymentsForMonth"){
                getPaymentsForMonth(call)
            }
        }
    }
}
