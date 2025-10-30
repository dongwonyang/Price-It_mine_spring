package project.price_it.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.price_it.dto.request.RequestDto;
import project.price_it.dto.request.RequestReqDto;
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
            HttpServletRequest request,
            @RequestBody RequestReqDto requestReqDto) {

        String token = jwtTokenProvider.resolveToken(request);
        Long userId = jwtTokenProvider.getUserIdFromAccessToken(token);

        RequestEntity requestEntity = RequestEntity.builder()
                .name(requestReqDto.getName())
                .pointPerPerson(requestReqDto.getPointPerPerson())
                .maxParticipants(requestReqDto.getMaxParticipants())
                .closingDate(requestReqDto.getClosingDate())
                .build();

        RequestEntity result = requestService.createRequest(requestEntity, userId, requestReqDto.getMart(), requestReqDto.getCategory());

        return ResponseEntity.ok(RequestDto.fromEntity(result));
    }
}
