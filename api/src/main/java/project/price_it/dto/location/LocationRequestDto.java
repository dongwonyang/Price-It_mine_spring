package project.price_it.dto.location;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class LocationRequestDto {
    private double lat;
    private double lng;
    private int radius; // 1 -> 1m
}
