package project.price_it.entity;

import jakarta.persistence.*;
import lombok.*;
import project.price_it.entity.point.PointEntity;
import project.price_it.entity.point.PointType;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;
    @Column
    private String password;

    @Column
    private String name;

    @Column
    private int point;

    @Column
    private Double lat;
    @Column
    private Double lng;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PointEntity> points = new ArrayList<>();
}
