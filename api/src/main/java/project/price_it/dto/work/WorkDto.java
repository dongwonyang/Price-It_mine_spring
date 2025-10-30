package project.price_it.dto.work;

import lombok.*;
import project.price_it.entity.WorkEntity;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class WorkDto {
    private long id;
    private int price;
    private String name;
    private long request_id;


    public static WorkEntity toEntity(WorkDto workDto) {
        return WorkEntity.builder()
                .id(workDto.getId())
                .price(workDto.getPrice())
                .name(workDto.getName())
                .build();
    }
    public static WorkDto fromEntity(WorkEntity work) {
        if (work == null) return null;

        return WorkDto.builder()
                .id(work.getId())
                .price(work.getPrice())
                .name(work.getName())
                .request_id(work.getRequest().getId())
                .build();
    }
}