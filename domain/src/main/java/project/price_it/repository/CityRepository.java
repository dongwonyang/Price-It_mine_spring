package project.price_it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.CityEntity;
import project.price_it.entity.DistrictEntity;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
}