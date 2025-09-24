package project.price_it.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.DistrictEntity;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
}
