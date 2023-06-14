package atomic_cinema_ru.utils

import io.ktor.server.application.*
import kotlinx.coroutines.flow.MutableStateFlow

object Values{
    var nameTableMovie  = MutableStateFlow("view_holder.showallmovie")

}

