package pk.edu.budget_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.domain.EntryType;
import pk.edu.budget_app.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserAndDateBetween(User user, LocalDateTime from, LocalDateTime to);

    List<Transaction> findByUser(User user);

    List<Transaction> findByUserAndTransactionTypeAndDateBetween(User user, EntryType type, LocalDateTime from, LocalDateTime to);
}
