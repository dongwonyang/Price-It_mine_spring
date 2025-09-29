package project.price_it.dto.mart;

import lombok.*;
import project.price_it.dto.district.DistrictDto;
import project.price_it.entity.DistrictEntity;
import project.price_it.entity.MartEntity;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class MartDto {
    private Long id;
    private String name;
    private double lat;
    private double lng;
    private Long districtId;

    public static MartDto fromEntity(MartEntity mart) {
        if (mart == null) return null;

        return MartDto.builder()
                .id(mart.getId())
                .name(mart.getName())
                .lat(mart.getLat())
                .lng(mart.getLng())
                .districtId(mart.getDistrict().getId())
                .build();
    }
}
