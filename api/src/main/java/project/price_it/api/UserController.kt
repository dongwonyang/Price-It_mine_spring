package project.price_it.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.price_it.dto.user.JoinDto
import project.price_it.dto.user.LoginRequestDto
import project.price_it.dto.user.JwtTokenDto
import project.price_it.dto.user.UserDto
import project.price_it.service.UserService
import project.price_it.security.JwtTokenProvider

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "회원 관련 API입니다.")
class UserController(
        private val userService: UserService,
        private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/join")
    @Operation(summary = "회원가입")
    fun createUser(@RequestBody request: JoinDto): ResponseEntity<UserDto> {
        val savedUser = request.let { userService.create(UserDto(email = it.email, password = it.password).toEntity()) }
        return ResponseEntity.ok(UserDto.fromEntity(savedUser))
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 및 Access Token 발급")
    fun login(@RequestBody request: LoginRequestDto): ResponseEntity<JwtTokenDto> {
        // 1️⃣ 사용자 인증
        val user = UserDto.fromEntity(userService.authenticate(request.email, request.password))

        // 2️⃣ Token 생성
        val accessToken = jwtTokenProvider.createAccessToken(user.id.toString())
        val refreshToken = jwtTokenProvider.createRefreshToken(user.id.toString())

        // 3️⃣ Response 반환
        return ResponseEntity.ok(JwtTokenDto(accessToken, refreshToken))
    }

    @PostMapping("/refresh")
    @Operation(summary = "access token 재발급", description = "accessToken null 가능")
    fun refreshAccessToken(@RequestBody request: JwtTokenDto): ResponseEntity<String> {
        val refreshToken = request.refreshToken
        val newAccessToken = jwtTokenProvider.refreshAccessToken(refreshToken)
        return ResponseEntity.ok(newAccessToken)
    }
}
