package project.price_it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.repository.RequestRepository;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
}
