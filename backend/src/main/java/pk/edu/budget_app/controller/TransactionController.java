package pk.edu.budget_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pk.edu.budget_app.domain.Transaction;
import pk.edu.budget_app.repository.TransactionRepository;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
