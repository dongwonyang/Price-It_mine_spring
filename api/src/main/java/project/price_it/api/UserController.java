package project.price_it.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.price_it.dto.user.LoginRequestDto;
import project.price_it.dto.user.LoginResponseDto;
import project.price_it.dto.user.UserDto;
import project.price_it.security.JwtTokenProvider;
import project.price_it.service.UserService;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "회원 관련 API입니다.")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    @Operation(
            summary = "회원가입"
    )
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(UserDto.fromEntity(userService.create(userDto.toEntity())));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 및 Access Token 발급")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        // 1️⃣ 사용자 인증
        UserDto user = UserDto.fromEntity(userService.authenticate(request.getEmail(), request.getPassword()));

        // 2️⃣ Token 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getId().toString());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId().toString());

        // 3️⃣ Response 반환
        return ResponseEntity.ok(new LoginResponseDto(accessToken, refreshToken));
    }
}
