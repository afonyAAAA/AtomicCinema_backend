package atomic_cinema_ru.features.register

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateSerializer : KSerializer<LocalDate>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalDate){
        val date = value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        encoder.encodeString(date)
    }
    override fun deserialize(decoder: Decoder): LocalDate = LocalDate.parse(decoder.decodeString())
}

@Serializable
data class RegisterReceiveRemote(
    val login : String,
    val password : String,
    @Serializable(with = LocalDateSerializer::class)
    val dateBirth : LocalDate,
    val firstName : String,
    val name : String,
    val lastName : String,
    val numberPhone : String,
    val idRole : Int
)
@Serializable
data class RegisterResponseRemote(
    val token: String
)
