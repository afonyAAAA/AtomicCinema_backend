package atomic_cinema_ru.features.register

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalDateSerializer : KSerializer<LocalDate>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalDate){
        val date = value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        encoder.encodeString(date)
    }
    override fun deserialize(decoder: Decoder): LocalDate = LocalDate.parse(decoder.decodeString())
}
object LocalDateTimeSerializer : KSerializer<LocalDateTime>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalDateTime){
        val date = value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        encoder.encodeString(date)
    }
    override fun deserialize(decoder: Decoder): LocalDateTime = LocalDateTime.parse(decoder.decodeString())
}

object LocalTimeSerializer : KSerializer<LocalTime>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalTime) {
        val time = value.format(DateTimeFormatter.ofPattern("HH:mm"))
        encoder.encodeString(time)
    }
    override fun deserialize(decoder: Decoder): LocalTime = LocalTime.parse(decoder.decodeString())
}


object BigDecimalSerializer : KSerializer<BigDecimal>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeString(value.toPlainString())
    }
    override fun deserialize(decoder: Decoder): BigDecimal {
        return decoder.decodeString().toBigDecimal()
    }
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
