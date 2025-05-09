package pk.edu.budget_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.domain.EntryType;
import pk.edu.budget_app.domain.Transaction;
import pk.edu.budget_app.repository.TransactionRepository;

import java.time.YearMonth;
import java.util.List;

import static pk.edu.budget_app.util.DateUtils.beginningOfMonth;
import static pk.edu.budget_app.util.DateUtils.endOfMonth;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByMonth(User user, YearMonth yearMonth) {
        var startMonth = beginningOfMonth(yearMonth);
        var endMonth = endOfMonth(yearMonth);
        return transactionRepository.findByUserAndDateBetween(user, startMonth, endMonth);
    }

    public List<Transaction> getTransactionsByMonthAndType(User user, YearMonth yearMonth, EntryType type) {
        var startMonth = beginningOfMonth(yearMonth);
        var endMonth = endOfMonth(yearMonth);
        return transactionRepository.findByUserAndTransactionTypeAndDateBetween(user, type, startMonth, endMonth);
    }
}
