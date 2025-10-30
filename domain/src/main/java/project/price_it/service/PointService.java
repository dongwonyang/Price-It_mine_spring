package project.price_it.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.entity.UserEntity;
import project.price_it.entity.point.PointEntity;
import project.price_it.entity.point.PointType;
import project.price_it.repository.PointRepository;
import project.price_it.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addPoint(Long userId, int amount, PointType type) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        if(type == PointType.USE && user.getPoint() > amount){
            throw new IllegalArgumentException(
                    "Lack of points. user: " + user.getPoint() +
                            ", need: " + amount
            );
        }

        PointEntity point = PointEntity.builder()
                .user(user)
                .amount(amount)
                .type(type)
                .date(LocalDateTime.now())
                .build();

        pointRepository.save(point);

        if (type == PointType.EARN) {
            user.setPoint(user.getPoint() + amount);
        } else {
            user.setPoint(user.getPoint() - amount);
        }
    }
}
