package project.price_it.dto.district;

import jakarta.persistence.*;
import lombok.*;
import project.price_it.dto.city.CityDto;
import project.price_it.dto.mart.MartDto;
import project.price_it.entity.CityEntity;
import project.price_it.entity.DistrictEntity;
import project.price_it.entity.MartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class DistrictDto {
    private Long id;
    private String name;
    private CityEntity city;
    private List<Long> martsId;

    public static DistrictDto fromEntity(DistrictEntity district) {
        if (district == null) return null;

        return DistrictDto.builder()
                .id(district.getId())
                .name(district.getName())
                .city(district.getCity())
                .martsId(district.getMarts()
                        .stream()
                        .map(d -> d.getId())
                        .collect(Collectors.toList()))
                .build();
    }
}
