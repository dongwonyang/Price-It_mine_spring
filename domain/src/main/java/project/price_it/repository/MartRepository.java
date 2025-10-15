package project.price_it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.MartEntity;

import java.util.Optional;

public interface MartRepository extends JpaRepository<MartEntity, Long> {
    Optional<MartEntity> findByName(String name);
}
