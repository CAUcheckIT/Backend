package capstone.checkIT.repository;

import capstone.checkIT.entity.TodoToday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoTodayRepository extends JpaRepository<TodoToday, Long> {

}
