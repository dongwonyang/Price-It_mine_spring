package project.price_it.dto.user;

import lombok.*;
import project.price_it.entity.UserEntity;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class UserDto {
    private Long id;

    private String email;
    private String password;

    private String name = "example";

    private Double lat = 0.0;
    private Double lng = 0.0;

    private int point = 0;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .name(name)
                .lat(lat)
                .lng(lng)
                .point(point)
                .email(email)
                .password(password)
                .build();
    }

    public static UserDto fromEntity(UserEntity user) {
        if (user == null) return null;

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .point(user.getPoint())
                .email(user.getEmail())
                .build();
    }
}


