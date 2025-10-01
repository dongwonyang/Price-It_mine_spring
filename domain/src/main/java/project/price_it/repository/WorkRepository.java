package project.price_it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.WorkEntity;

public interface WorkRepository extends JpaRepository<WorkEntity, Long> {
}
