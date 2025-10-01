package project.price_it.dto.request;

import lombok.*;
import project.price_it.dto.mart.MartDto;
import project.price_it.entity.MartEntity;
import project.price_it.entity.RequestEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class RequestDto {
    private long id;

    private String category;
    private String mart;
    private String name;
    private int pointPerPerson;
    private int maxParticipants;
    private LocalDateTime closingDate;

    public static RequestDto fromEntity(RequestEntity request) {
        if (request == null) return null;

        return RequestDto.builder()
                .id(request.getId())
                .category(request.getCategory().getName())   // CategoryEntity → 이름
                .mart(request.getMart().getName())           // MartEntity → 이름
                .name(request.getName())
                .pointPerPerson(request.getPointPerPerson())
                .maxParticipants(request.getMaxParticipants())
                .closingDate(request.getClosingDate())
                .build();
    }
}
