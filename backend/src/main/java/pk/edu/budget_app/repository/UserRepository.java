package pk.edu.budget_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.edu.budget_app.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

}
