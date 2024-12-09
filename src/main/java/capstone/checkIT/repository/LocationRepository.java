package capstone.checkIT.repository;

import capstone.checkIT.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByStartTime(Timestamp startTime);
}
