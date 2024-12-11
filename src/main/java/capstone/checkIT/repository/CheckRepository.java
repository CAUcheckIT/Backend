package capstone.checkIT.repository;

import capstone.checkIT.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findByProductId(Long productId);
}
