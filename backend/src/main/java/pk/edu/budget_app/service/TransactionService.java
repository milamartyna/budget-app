package pk.edu.budget_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pk.edu.budget_app.domain.Category;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.domain.EntryType;
import pk.edu.budget_app.domain.Transaction;
import pk.edu.budget_app.dto.TransactionDto;
import pk.edu.budget_app.repository.TransactionRepository;
import pk.edu.budget_app.util.DateUtils;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static pk.edu.budget_app.util.DateUtils.beginningOfMonth;
import static pk.edu.budget_app.util.DateUtils.endOfMonth;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public Transaction saveAndCharge(TransactionDto dto) {
        var user = userService.getUserOrThrow(dto.getAccountName());
        var category = getCategory(user, dto);
        var amount = dto.getAmount();
        var date = getDate(dto);

        var type = dto.getTransactionType();
        switch (type) {
            case INCOME -> userService.addIncome(user, amount);
            case EXPENSE -> userService.addExpense(user, amount);
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + type);
        }

        var transaction = Transaction.builder()
            .amount(amount)
            .transactionType(type)
            .user(user)
            .category(category)
            .date(date)
            .description(dto.getDescription())
            .build();

        return transactionRepository.save(transaction);
    }

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

    private Category getCategory(User user, TransactionDto dto) {
        return Optional.ofNullable(dto.getCategoryName())
                .map(name -> categoryService.getByUserAndName(user, name)
                        .orElseThrow(() -> new IllegalArgumentException("Category {} not found for user {} " + name + user.getName())))
                .orElse(null);
    }

    private LocalDateTime getDate(TransactionDto dto) {
        return Optional.ofNullable(dto.getDate())
                .orElseGet(DateUtils::now);
    }
}
