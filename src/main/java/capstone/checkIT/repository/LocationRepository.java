package capstone.checkIT.repository;

import capstone.checkIT.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    // 특정 startTime으로 관련된 모든 위치 데이터 조회
    List<Location> findByStartTime(Timestamp startTime);

    // 특정 디바이스의 가장 최신 startTime 조회
    @Query("SELECT MAX(l.startTime) FROM Location l WHERE l.id IN " +
            "(SELECT dl.location.id FROM DeviceLocation dl WHERE dl.device.id = :deviceId)")
    Optional<Timestamp> findLatestStartTimeByDeviceId(@Param("deviceId") Long deviceId);

    @Query("SELECT l FROM Location l JOIN DeviceLocation dl ON l.id = dl.location.id " +
            "WHERE dl.device.id = :deviceId ORDER BY l.time DESC")
    List<Location> findLatestLocationsByDeviceId(@Param("deviceId") Long deviceId);
}
