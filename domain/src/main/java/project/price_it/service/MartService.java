package project.price_it.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.price_it.entity.MartEntity;
import project.price_it.repository.MartRepository;

import java.security.PublicKey;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MartService {
    private final MartRepository martRepository;

    public List<MartEntity> getMartByDistrict(String districtName){
        return martRepository.findByDistrict_Name(districtName);
    }
}
