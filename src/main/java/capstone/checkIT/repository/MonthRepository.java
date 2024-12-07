package capstone.checkIT.repository;

import capstone.checkIT.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthRepository extends JpaRepository<Month, Long> {
    List<Month> findByMemberId(Long memberId);

}
