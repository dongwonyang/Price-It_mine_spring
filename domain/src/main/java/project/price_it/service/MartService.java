package project.price_it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.price_it.repository.MartRepository;

@Service
@RequiredArgsConstructor
public class MartService {
    private final MartRepository martRepository;
}
