package project.price_it.dto.city;

import lombok.*;
import project.price_it.dto.district.DistrictDto;
import project.price_it.dto.user.UserDto;
import project.price_it.entity.CityEntity;
import project.price_it.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class CityDto {
    private Long id;
    private String name;
    private List<Long> districtsId;

    public static CityDto fromEntity(CityEntity city) {
        if (city == null) return null;

        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .districtsId(city.getDistricts()
                        .stream()
                        .map(d -> d.getId())
                        .collect(Collectors.toList()))
                .build();
    }
}
