package project.price_it.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.price_it.dto.location.LocationRequestDto;
import project.price_it.dto.mart.MartDto;
import project.price_it.service.LocationService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/location")
@Tag(name = "Location API", description = "위치 관련 API입니다.")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/marts/nearby")
    @Operation(summary = "위치 기반 주위 마트 가져오기", description = "radius 1 = 1m")
    public ResponseEntity<List<MartDto>> getNearbyMarts(
            @RequestBody LocationRequestDto locationRequestDto
    ) {
        double lat = locationRequestDto.getLat();
        double lng = locationRequestDto.getLng();
        int radius = locationRequestDto.getRadius();

        List<MartDto> result = locationService.getNearbyMarts(lat, lng, radius)
                .stream()
                .map(MartDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


}
