package pk.edu.budget_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.edu.budget_app.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
