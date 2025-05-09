package pk.edu.budget_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pk.edu.budget_app.domain.EntryType;
import pk.edu.budget_app.dto.TransactionDto;
import pk.edu.budget_app.dto.TransactionMapper;
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
        return transactionService
                .getAllTransactions()
                .stream()
                .map(TransactionMapper::toDto)
                .toList();
    }

    @GetMapping("/user/{userName}/{yearMonth}")
    public List<TransactionDto> getTransactionsByMonth(
            @PathVariable String userName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
            @RequestParam Optional<EntryType> transactionType) {

        var user = userService.getUserOrThrow(userName);

        var transactions = transactionType
                .map(type -> transactionService.getTransactionsByMonthAndType(user, yearMonth, type))
                .orElseGet(() -> transactionService.getTransactionsByMonth(user, yearMonth));

        return transactions.stream()
                .map(TransactionMapper::toDto)
                .toList();
    }

    @PostMapping
    public TransactionDto createTransaction(@RequestBody TransactionDto transaction) {
        var transactionEntity = transactionService.saveAndCharge(transaction);
        return TransactionMapper.toDto(transactionEntity);
    }

}
