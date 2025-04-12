package pk.edu.budget_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.edu.budget_app.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
