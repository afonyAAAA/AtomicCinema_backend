package atomic_cinema_ru.security.token

interface TokenService {
    fun generate(
        config: TokenConfig,
        vararg claims : TokenClaim
    ): String
}