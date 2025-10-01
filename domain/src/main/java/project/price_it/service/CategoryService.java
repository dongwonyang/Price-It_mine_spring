package project.price_it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
}
