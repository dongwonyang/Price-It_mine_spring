package project.price_it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.MartEntity;

public interface MartRepository extends JpaRepository<MartEntity, Long> {
}
