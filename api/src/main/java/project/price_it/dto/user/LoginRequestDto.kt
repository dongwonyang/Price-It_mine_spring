package project.price_it.dto.user

import lombok.Data

data class LoginRequestDto(
        val email: String = "",
        val password: String = ""
)

