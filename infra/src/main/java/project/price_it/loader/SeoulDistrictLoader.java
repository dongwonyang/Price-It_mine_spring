package project.price_it.loader;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import project.price_it.entity.CategoryEntity;
import project.price_it.entity.CityEntity;
import project.price_it.entity.DistrictEntity;
import project.price_it.entity.MartEntity;
import project.price_it.repository.CategoryRepository;
import project.price_it.repository.CityRepository;
import project.price_it.repository.DistrictRepository;
import project.price_it.repository.MartRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional
public class SeoulDistrictLoader implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final MartRepository martRepository;
    private final CategoryRepository categoryRepository;


    public SeoulDistrictLoader(CityRepository cityRepository, DistrictRepository districtRepository, MartRepository martRepository, CategoryRepository categoryRepository) {
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.martRepository = martRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initDistrict();
        initCategory();
        initMart();

        System.out.println("SeoulDistrictLoader 실행됨");
    }

    private void initDistrict() {
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
    }

    private void initMart() {
        try {
            String apiKey = "49714d4552646964313039556e4a4f6b";
            String urlStr = "http://openapi.seoul.go.kr:8088/" + apiKey + "/xml/LOCALDATA_082501/1/5/";
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);

            NodeList rows = doc.getElementsByTagName("row"); // <row> 단위로 데이터

            for (int i = 0; i < rows.getLength(); i++) {
                Element row = (Element) rows.item(i);

                String name = row.getElementsByTagName("BPLCNM").item(0).getTextContent();
                String address = row.getElementsByTagName("SITEWHLADDR").item(0).getTextContent();
                double x = Double.parseDouble(row.getElementsByTagName("X").item(0).getTextContent());
                double y = Double.parseDouble(row.getElementsByTagName("Y").item(0).getTextContent());

                Pattern pattern = Pattern.compile("\\S+구");
                Matcher matcher = pattern.matcher(address);

                String guName;
                if (matcher.find()) {
                    guName = matcher.group();
                } else {
                    guName = null;
                }

                if (guName != null) {
                    DistrictEntity district = districtRepository.findByName(guName)
                            .orElseThrow(() -> new IllegalArgumentException("District not found: " + guName));

                    MartEntity mart = MartEntity.builder()
                            .name(name)
                            .lat(y)  // Y -> lat
                            .lng(x)  // X -> lng
                            .district(district)
                            .build();

                    System.out.println(mart);
                    martRepository.save(mart);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCategory() {
        String[] categories = {"과일", "채소", "육류", "수산물", "유제품", "간식", "음료"};
        for (String catName : categories) {
            CategoryEntity category = CategoryEntity.builder()
                    .name(catName)
                    .build();
            categoryRepository.save(category);
        }
    }
}

