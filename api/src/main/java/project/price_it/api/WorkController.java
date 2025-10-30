package project.price_it.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.price_it.dto.work.WorkDto;
import project.price_it.dto.work.WorkRequestDto;
import project.price_it.entity.WorkEntity;
import project.price_it.security.JwtTokenProvider;
import project.price_it.service.WorkService;

@Controller
@RequestMapping("/works")
@Tag(name = "Work API", description = "작업 관련 API입니다.")
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    @Operation(summary = "작업 생성")
    public ResponseEntity<WorkDto> createWork(HttpServletRequest request, @RequestBody WorkRequestDto workRequestDto) {
        String token = jwtTokenProvider.resolveToken(request);
        Long userId = jwtTokenProvider.getUserIdFromAccessToken(token);

        long requestId = workRequestDto.getRequest_id();
        WorkEntity workEntity = WorkEntity.builder()
                .price(workRequestDto.getPrice())
                .name(workRequestDto.getName())
                .build();

        return ResponseEntity.ok(
                WorkDto.fromEntity(
                        workService.createWork(
                                workEntity,
                                userId,
                                requestId
                        )
                )
        );
    }
}
