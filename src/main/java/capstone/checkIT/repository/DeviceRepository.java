package capstone.checkIT.repository;

import capstone.checkIT.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByMemberId(Long memberId);
}
