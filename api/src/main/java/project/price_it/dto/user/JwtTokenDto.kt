package project.price_it.dto.user

data class JwtTokenDto(
        val accessToken: String = "",
        val refreshToken: String = ""
)
