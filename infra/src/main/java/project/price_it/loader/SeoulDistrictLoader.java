package project.price_it.loader;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.price_it.entity.CityEntity;
import project.price_it.entity.DistrictEntity;
import project.price_it.repository.CityRepository;

import java.util.ArrayList;

@Component
@Transactional
public class SeoulDistrictLoader implements CommandLineRunner {

    private final CityRepository cityRepository;

    public SeoulDistrictLoader(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 서울시 생성
        CityEntity seoul = CityEntity.builder()
                .name("서울시")
                .build();
        seoul.setDistricts(new ArrayList<>());

        // 서울시 모든 구 이름 배열
        String[] districts = {
                "강남구", "강동구", "강북구", "강서구", "관악구",
                "광진구", "구로구", "금천구", "노원구", "도봉구",
                "동대문구", "동작구", "마포구", "서대문구", "서초구",
                "성동구", "성북구", "송파구", "양천구", "영등포구",
                "용산구", "은평구", "종로구", "중구", "중랑구"
        };

        // DistrictEntity 생성 후 City에 연결
        for (String name : districts) {
            DistrictEntity district = DistrictEntity.builder()
                    .name(name)
                    .city(seoul)
                    .build();
            seoul.getDistricts().add(district);
        }

        // DB 저장 (CascadeType.ALL로 District도 자동 저장)
        cityRepository.save(seoul);

        System.out.println("SeoulDistrictLoader 실행됨");
    }
}

