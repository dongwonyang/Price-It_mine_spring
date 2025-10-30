package project.price_it.dto.work;

import lombok.Data;

@Data
public class WorkRequestDto {
    private int price;
    private String name;
    private long request_id;
}
