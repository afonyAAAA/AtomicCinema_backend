package atomic_cinema_ru.security.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
