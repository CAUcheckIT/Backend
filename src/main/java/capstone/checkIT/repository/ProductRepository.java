package capstone.checkIT.repository;

import capstone.checkIT.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByTodoId(Long todoId);
    Optional<Product> findByMemberIdAndId(Long memberId, Long id);
}
