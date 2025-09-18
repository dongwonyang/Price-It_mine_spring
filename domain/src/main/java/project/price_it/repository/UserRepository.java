package project.price_it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.price_it.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}