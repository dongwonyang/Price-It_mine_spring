package project.price_it.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.price_it.dto.city.CityRequestDto;
import project.price_it.dto.city.CityResponseDto;
import project.price_it.dto.district.DistrictResponseDto;
import project.price_it.service.CityService;
import project.price_it.service.DistrictService;
import project.price_it.service.MartService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
@Tag(name = "Search API", description = "검색 관련 API입니다.")
@RequiredArgsConstructor
public class SearchController {
    private final MartService martService;
    private final DistrictService districtService;
    private final CityService cityService;


    @GetMapping("/cities")
    @Operation(summary = "city 모두 반환")
    public ResponseEntity<List<CityResponseDto>> getCities() {
        return ResponseEntity.ok(cityService.getAll().stream()
                .map(d -> new CityResponseDto(d.getName()))
                .collect(Collectors.toList()));
    }

    @PostMapping("/districts")
    @Operation(summary = "city 입력 시 district 반환")
    public ResponseEntity<List<DistrictResponseDto>> getDistrict(@RequestBody CityRequestDto cityRequestDto){
        return ResponseEntity.ok(districtService.getDistrictByCity(cityRequestDto.getCityName()).stream()
                .map(d -> new DistrictResponseDto(d.getName()))
                .collect(Collectors.toList()));
    }
}
