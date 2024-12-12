package capstone.checkIT.repository;

import capstone.checkIT.entity.TodoToday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoTodayRepository extends JpaRepository<TodoToday, Long> {
    Optional<TodoToday> findByname(String name);
    Optional<TodoToday> findByMemberIdAndId(Long memberId, Long id);
}
