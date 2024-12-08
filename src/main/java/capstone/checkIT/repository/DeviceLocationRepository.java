package capstone.checkIT.repository;

import capstone.checkIT.entity.DeviceLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceLocationRepository extends JpaRepository<DeviceLocation, Long> {

}
