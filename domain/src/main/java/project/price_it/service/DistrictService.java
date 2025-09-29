package project.price_it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.price_it.entity.DistrictEntity;
import project.price_it.repository.DistrictRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;


    public List<DistrictEntity> getDistrictByCity(String cityName) {
        return districtRepository.findByCity_Name(cityName);
    }

    public List<DistrictEntity> getAll() {
        return districtRepository.findAll();
    }
}
