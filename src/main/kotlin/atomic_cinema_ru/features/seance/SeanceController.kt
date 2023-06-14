package atomic_cinema_ru.features.seance

import atomic_cinema_ru.database.seances.Seances
import atomic_cinema_ru.database.seances.SeancesDTO
import atomic_cinema_ru.database.seances.SeancesView
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.datetime.*

suspend fun checkDate(dateStart : LocalDate, dateEnd : LocalDate, call: ApplicationCall): Boolean {
    if(dateStart > dateEnd && dateStart < java.time.LocalDate.now().toKotlinLocalDate()){
        call.respond(HttpStatusCode.BadRequest,
            "Дата начала не может быть больше даты конца")
        return false
    }
    return true
}

suspend fun checkTime(timeStart : LocalTime, timeEnd : LocalTime, call: ApplicationCall) : Boolean{
    if(timeStart > timeEnd){
        call.respond(HttpStatusCode.BadRequest,
            "Время начала не может быть больше времени конца")
        return false
    }
    return true
}

suspend fun deletePastSeance(seances: List<SeancesDTO>, newSeance: SeancesDTO) = coroutineScope{
    launch {
        val listDeleteSeances = mutableListOf<SeancesDTO>()

        seances.forEach { seance ->
            if(seance.dateEnd < newSeance.dateStart){
                listDeleteSeances.add(seance)
            }
        }

        if(listDeleteSeances.isNotEmpty()){
            listDeleteSeances.forEach{ seance ->
                Seances.delete(seance.id)
            }
        }
    }
}

suspend fun checkSeancesConflict(seancesDTO: SeancesDTO, seances : List<SeancesDTO>, call: ApplicationCall) : Boolean{
    seances.forEach { existingSeances ->
        if(seancesDTO.timeStart <= existingSeances.timeEnd && seancesDTO.timeStart >= existingSeances.timeStart) {
            call.respond(HttpStatusCode.Conflict, "Конфликт с уже существующими сеансами")
            return true
        }
    }
    return false
}

suspend fun getPresentSeancesCinema(call: ApplicationCall){
    val receive = call.receive<SeancesCinema>()

    val result = SeancesView.getSeancesCinema(receive.addressCinema)

    val listSeance = result.map { item ->
        SeanceResponseRemote(
            id = item.id,
            timeStart = item.timeStart.toJavaLocalTime(),
            timeEnd = item.timeEnd.toJavaLocalTime(),
            dateStart = item.dateStart.toJavaLocalDate(),
            dateEnd = item.dateEnd.toJavaLocalDate(),
            price = item.price,
            idMovie = item.idMovie,
            idHall = item.idHall,
            nameTypeHall = item.nameTypeHall,
            addressCinema = item.addressCinema
        )
    }
    call.respond(listSeance)
}

suspend fun getSeanceMovieById(call : ApplicationCall){
    val receive = call.receive<SeanceReceiveRemote>()

    val seancesDTO = SeancesView.getSeanceMovie(receive.idMovie)

    if(seancesDTO.isNotEmpty()){
        val seances = mutableListOf<SeanceResponseRemote>()

        seancesDTO.forEach{ item ->
            if(item != null){
                val seance = SeanceResponseRemote(
                    id = item.id,
                    timeStart = item.timeStart.toJavaLocalTime(),
                    timeEnd = item.timeEnd.toJavaLocalTime(),
                    dateStart = item.dateStart.toJavaLocalDate(),
                    dateEnd = item.dateEnd.toJavaLocalDate(),
                    price = item.price,
                    idMovie = item.idMovie,
                    idHall = item.idHall,
                    nameTypeHall = item.nameTypeHall,
                    addressCinema = item.addressCinema
                )
                seances.add(seance)
            }
        }

        call.respond(seances)

    }else{
        call.respond(HttpStatusCode.NotFound)
    }
}

suspend fun deleteSeance(call: ApplicationCall){
    val receive = call.receive<SeanceDeleteReceiveRemote>()

    Seances.delete(receive.id)

    call.respond(HttpStatusCode.OK)
}

suspend fun updateSeance(call: ApplicationCall) {
    val receive = call.receive<SeanceUpdateReceiveRemote>()

    if(checkDate(receive.dateStart.toKotlinLocalDate(), receive.dateEnd.toKotlinLocalDate(), call) &&
        checkTime(receive.timeStart.toKotlinLocalTime(), receive.timeEnd.toKotlinLocalTime(), call)){

        val seanceDTO = SeancesDTO(
            id = receive.id,
            idHall = receive.idHall,
            idMovie = receive.idMovie,
            dateEnd = receive.dateEnd.toKotlinLocalDate(),
            dateStart = receive.dateStart.toKotlinLocalDate(),
            timeEnd = receive.timeEnd.toKotlinLocalTime(),
            timeStart = receive.timeStart.toKotlinLocalTime(),
            price = receive.price.toBigDecimal()
        )
        val seances = SeancesView.getSeancesByIdHall(receive.idHall).filter{it.id != seanceDTO.id}

        if(seances.isNotEmpty()){
            if(!checkSeancesConflict(seanceDTO, seances, call)){
                deletePastSeance(seances, seanceDTO)
                try {
                    Seances.update(seanceDTO)

                    call.respond(HttpStatusCode.OK)
                }catch (e : Exception){
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }else{
            try {
                Seances.update(seanceDTO)

                call.respond(HttpStatusCode.OK)
            }catch (e : Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }

    }
}

suspend fun addSeance(call : ApplicationCall){
    val receive = call.receive<SeanceAddReceiveRemote>()

    if(checkDate(receive.dateStart.toKotlinLocalDate(), receive.dateEnd.toKotlinLocalDate(), call) &&
        checkTime(receive.timeStart.toKotlinLocalTime(), receive.timeEnd.toKotlinLocalTime(), call)){

        val seanceDTO = SeancesDTO(
            idHall = receive.idHall,
            idMovie = receive.idMovie,
            dateEnd = receive.dateEnd.toKotlinLocalDate(),
            dateStart = receive.dateStart.toKotlinLocalDate(),
            timeEnd = receive.timeEnd.toKotlinLocalTime(),
            timeStart = receive.timeStart.toKotlinLocalTime(),
            price = receive.price.toBigDecimal()
        )

        val seances = SeancesView.getSeancesByIdHall(receive.idHall)

        if(seances.isNotEmpty()){
            if(!checkSeancesConflict(seanceDTO, seances, call)){
                deletePastSeance(seances, seanceDTO)
                try {
                    Seances.insert(seanceDTO)

                    call.respond(HttpStatusCode.OK)
                }catch (e : Exception){
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }else{
            try {
                Seances.insert(seanceDTO)

                call.respond(HttpStatusCode.OK)
            }catch (e : Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

