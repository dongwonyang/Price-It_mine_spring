package project.price_it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.repository.WorkRepository;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;
}
