package project.price_it.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.price_it.entity.MartEntity;
import project.price_it.repository.DistrictRepository;
import project.price_it.repository.MartRepository;
import project.price_it.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;
    private final MartRepository martRepository;


    @Transactional()
    public List<MartEntity> getNearbyMarts(double lat, double lng, int radius) {
        return martRepository.findNearbyMarts(lat, lng, radius);
    }
}
