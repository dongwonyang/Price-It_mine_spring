package project.price_it.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
        initMartWithJson();

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

    private void initMartWithAPI() {
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

    private void initMartWithJson() {
        try {
            // 1️⃣ resources 폴더의 JSON 파일 로드
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("seoul_market_data.json");
            if (inputStream == null) {
                throw new FileNotFoundException("JSON 파일을 찾을 수 없습니다: seoul_mart_data.json");
            }

            // 2️⃣ JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(inputStream);

            // 3️⃣ DATA 배열 추출
            JsonNode dataArray = rootNode.path("DATA");
            if (dataArray.isMissingNode()) {
                throw new IllegalArgumentException("DATA 필드가 존재하지 않습니다.");
            }

            // 4️⃣ 각 항목 순회
            for (JsonNode node : dataArray) {
                String name = node.path("bplcnm").asText(null);
                String address = node.path("sitewhladdr").asText(null);
                double x = node.path("x").asDouble();
                double y = node.path("y").asDouble();

                if (address == null || name == null) continue;

                // 구 이름 추출 (예: "서울특별시 강동구 둔촌동..." → "강동구")
                Pattern pattern = Pattern.compile("\\S+구");
                Matcher matcher = pattern.matcher(address);

                String guName = null;
                if (matcher.find()) {
                    guName = matcher.group();
                }

                if (guName != null) {
                    Optional<DistrictEntity> districtOpt = districtRepository.findByName(guName);
                    if (districtOpt.isEmpty()) {
                        System.out.println("❌ District not found: " + guName);
                        continue;
                    }

                    DistrictEntity district = districtOpt.get();

                    MartEntity mart = MartEntity.builder()
                            .name(name)
                            .lat(y) // Y → lat
                            .lng(x) // X → lng
                            .district(district)
                            .build();

                    System.out.println(mart);
                    martRepository.save(mart);
                }
            }

            System.out.println("✅ JSON에서 마트 데이터 초기화 완료");

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

