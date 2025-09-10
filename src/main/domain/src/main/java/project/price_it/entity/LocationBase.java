package project.price_it.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class LocationBase {
    @Column
    private Double lat;
    @Column
    private Double lng;
}
