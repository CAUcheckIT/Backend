package capstone.checkIT.repository;

import capstone.checkIT.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonthRepository extends JpaRepository<Month, Long> {
    Optional<Month> findByIdAndMemberId(Long Id, Long memberId);

}
