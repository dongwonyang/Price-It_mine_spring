package project.price_it.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.DistrictEntity;

import java.util.List;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
    List<DistrictEntity> findByCity_Name(String cityName);
}
