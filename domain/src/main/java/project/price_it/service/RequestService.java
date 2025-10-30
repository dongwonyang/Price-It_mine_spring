package project.price_it.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.entity.CategoryEntity;
import project.price_it.entity.MartEntity;
import project.price_it.entity.RequestEntity;
import project.price_it.entity.UserEntity;
import project.price_it.entity.point.PointEntity;
import project.price_it.entity.point.PointType;
import project.price_it.repository.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MartRepository martRepository;
    private final PointService pointService;

    @Transactional
    public RequestEntity createRequest(RequestEntity requestEntity, Long requesterId, String martName, String categoryName) {
        UserEntity requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        CategoryEntity category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        MartEntity mart = martRepository.findByName(martName)
                .orElseThrow(() -> new IllegalArgumentException("Mart not found"));

        int requiredPoints = requestEntity.getPointPerPerson() * requestEntity.getMaxParticipants();
        pointService.addPoint(requesterId, requiredPoints, PointType.USE);

        RequestEntity request = RequestEntity.builder()
                .requester(requester)
                .category(category)
                .mart(mart)
                .name(requestEntity.getName())
                .pointPerPerson(requestEntity.getPointPerPerson())
                .maxParticipants(requestEntity.getMaxParticipants())
                .closingDate(requestEntity.getClosingDate())
                .build();

        return requestRepository.save(request);
    }
}
