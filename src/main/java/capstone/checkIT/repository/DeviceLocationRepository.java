package capstone.checkIT.repository;

import capstone.checkIT.entity.DeviceLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface DeviceLocationRepository extends JpaRepository<DeviceLocation, Long> {
    @Modifying
    @Query("UPDATE DeviceLocation dl SET dl.isFinished = true " +
            "WHERE dl.location.startTime = :startTime " +
            "AND dl.device.member.id = :memberId")
    int updateIsFinishedByStartTimeAndMemberId(@Param("startTime") Timestamp startTime, @Param("memberId") Long memberId);
}
