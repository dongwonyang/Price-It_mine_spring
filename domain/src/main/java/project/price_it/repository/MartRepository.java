package project.price_it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.price_it.entity.DistrictEntity;
import project.price_it.entity.MartEntity;

import java.util.List;
import java.util.Optional;

public interface MartRepository extends JpaRepository<MartEntity, Long> {
    Optional<MartEntity> findByName(String name);

    List<MartEntity> findByDistrict_Name(String DistrictName);

    @Query("SELECT m FROM MartEntity m WHERE " +
            "(6371000 * acos(" +
            "cos(radians(:lat)) * cos(radians(m.lat)) * " +
            "cos(radians(m.lng) - radians(:lng)) + " +
            "sin(radians(:lat)) * sin(radians(m.lat))" +
            ")) <= :radius")
    List<MartEntity> findNearbyMarts(@Param("lat") double lat,
                                     @Param("lng") double lng,
                                     @Param("radius") double radius);

}
