package project.price_it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.point.PointEntity;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
}
