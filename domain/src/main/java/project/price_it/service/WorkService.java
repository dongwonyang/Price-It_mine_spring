package project.price_it.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.entity.RequestEntity;
import project.price_it.entity.UserEntity;
import project.price_it.entity.WorkEntity;
import project.price_it.repository.RequestRepository;
import project.price_it.repository.WorkRepository;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;
    private final RequestRepository requestRepository;

    @Transactional
    public WorkEntity createWork(WorkEntity workEntity, long requesterId) {
        RequestEntity request = requestRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("request not found"));

        workEntity.setRequest(request);
        return workRepository.save(workEntity);
    }
}
