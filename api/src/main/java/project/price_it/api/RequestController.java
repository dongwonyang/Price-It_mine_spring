package project.price_it.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.price_it.dto.request.RequestDto;
import project.price_it.entity.RequestEntity;
import project.price_it.security.JwtTokenProvider;
import project.price_it.service.PointService;
import project.price_it.service.RequestService;
import project.price_it.service.WorkService;

@Controller
@RequestMapping("/requests")
@Tag(name = "Request API", description = "의뢰 관련 API입니다.")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final WorkService workService;
    private final PointService pointService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    @Operation(summary = "의뢰 생성")
    public ResponseEntity<RequestDto> createRequest(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody RequestDto requestDto) {

        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.getUserIdFromAccessToken(token);

        RequestEntity requestEntity = RequestEntity.builder()
                .name(requestDto.getName())
                .pointPerPerson(requestDto.getPointPerPerson())
                .maxParticipants(requestDto.getMaxParticipants())
                .closingDate(requestDto.getClosingDate())
                .build();

        RequestEntity result = requestService.createRequest(requestEntity, userId, requestDto.getMart(), requestDto.getCategory());

        return ResponseEntity.ok(RequestDto.fromEntity(result));
    }
}
