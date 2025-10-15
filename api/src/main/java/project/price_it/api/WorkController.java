package project.price_it.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.price_it.dto.work.WorkDto;
import project.price_it.service.WorkService;

@Controller
@RequestMapping("/works")
@Tag(name = "Work API", description = "작업 관련 API입니다.")
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;

    @PostMapping("/create")
    @Operation(summary = "작업 생성")
    public ResponseEntity<WorkDto> createWork(@RequestBody WorkDto workDto) {
        return ResponseEntity.ok(
                WorkDto.fromEntity(
                        workService.createWork(WorkDto.toEntity(workDto), workDto.getRequest_id())
                )
        );
    }
}
