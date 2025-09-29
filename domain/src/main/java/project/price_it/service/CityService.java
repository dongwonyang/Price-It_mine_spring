package project.price_it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.price_it.entity.CityEntity;
import project.price_it.repository.CityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<CityEntity> getAll(){
        return cityRepository.findAll();
    }
}
