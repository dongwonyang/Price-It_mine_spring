package project.price_it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
