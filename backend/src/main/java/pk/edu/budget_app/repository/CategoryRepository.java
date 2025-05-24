package pk.edu.budget_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.edu.budget_app.domain.Category;
import pk.edu.budget_app.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByUserAndName(User user, String name);
    List<Category> findAllByUser(User user);

}
