package project.price_it.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class UserDto {
    private Long id;
    private String name;
    private Double lat;
    private Double lng;

    public UserEntity toEntity(){
        UserEntity()
    }
}
