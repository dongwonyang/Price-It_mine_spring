package project.price_it.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.entity.RequestEntity;
import project.price_it.entity.UserEntity;
import project.price_it.entity.WorkEntity;
import project.price_it.repository.RequestRepository;
import project.price_it.repository.UserRepository;
import project.price_it.repository.WorkRepository;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Transactional
    public WorkEntity createWork(WorkEntity workEntity, long workerId, long requestId) {
        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("request not found"));

        UserEntity worker = userRepository.findById(workerId)
                .orElseThrow(() -> new IllegalArgumentException("worker not found"));

        workEntity.setRequest(request);
        workEntity.setWorker(worker);
        return workRepository.save(workEntity);
    }
}
