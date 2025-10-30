package project.price_it.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestReqDto {
    private String category;
    private String mart;
    private String name;
    private int pointPerPerson;
    private int maxParticipants;
    private LocalDateTime closingDate;
}
