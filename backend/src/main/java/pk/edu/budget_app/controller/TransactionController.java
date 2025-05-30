package pk.edu.budget_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pk.edu.budget_app.domain.EntryType;
import pk.edu.budget_app.dto.TransactionDto;
import pk.edu.budget_app.service.UserService;
import pk.edu.budget_app.service.TransactionService;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final UserService userService;
    private final TransactionService transactionService;

    @GetMapping
    public List<TransactionDto> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/user/{userName}")
    public List<TransactionDto> getTransactions(
            @PathVariable String userName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
            @RequestParam Optional<EntryType> transactionType) {

        var user = userService.getUserOrThrow(userName);

        if (yearMonth != null) {
            return transactionType
                    .map(type -> transactionService.getTransactionsByMonthAndType(user, yearMonth, type))
                    .orElseGet(() -> transactionService.getTransactionsByMonth(user, yearMonth));
        }

        return transactionService.getAllTransactionsForUser(user);
    }

    @PostMapping
    public TransactionDto createTransaction(@RequestBody TransactionDto transaction) {
        return transactionService.saveAndCharge(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id, @RequestParam String accountName) {
        transactionService.deleteTransaction(id, accountName);
    }

}
