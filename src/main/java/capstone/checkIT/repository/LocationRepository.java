package capstone.checkIT.repository;

import capstone.checkIT.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByStartTime(Timestamp startTime);

    // 최신 startTime 찾기
    @Query("SELECT MAX(l.startTime) FROM Location l WHERE l.id IN " +
            "(SELECT dl.location.id FROM DeviceLocation dl WHERE dl.device.id = :deviceId)")
    Optional<Timestamp> findLatestStartTimeByDeviceId(@Param("deviceId") Long deviceId);

}
