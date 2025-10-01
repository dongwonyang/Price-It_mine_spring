package project.price_it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.repository.PointRepository;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
}
