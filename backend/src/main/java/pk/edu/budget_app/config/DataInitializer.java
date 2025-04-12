package pk.edu.budget_app.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import pk.edu.budget_app.domain.*;
import pk.edu.budget_app.repository.*;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

// initial data loading for development purposes
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @PostConstruct
    public void initData() {
        //Clear database
        transactionRepository.deleteAll();
        categoryRepository.deleteAll();
        accountRepository.deleteAll();

        // Create users
        Account alice = accountRepository.save(Account.builder()
                .name("Alice")
                .build());

        Account bob = accountRepository.save(Account.builder()
                .name("Bob")
                .build());

        // Create categories
        Category food = categoryRepository.save(Category.builder().name("Food").build());
        Category rent = categoryRepository.save(Category.builder().name("Rent").build());
        Category salary = categoryRepository.save(Category.builder().name("Salary").build());
        Category travel = categoryRepository.save(Category.builder().name("Travel").build());

        // Create transactions
        transactionRepository.saveAll(List.of(
                Transaction.builder()
                        .amount(120.0)
                        .transactionType(EntryType.EXPENSE)
                        .category(food)
                        .date(LocalDateTime.now().minusDays(3))
                        .account(alice)
                        .build(),

                Transaction.builder()
                        .amount(2000.0)
                        .transactionType(EntryType.INCOME)
                        .category(salary)
                        .date(LocalDateTime.now().minusDays(5))
                        .account(alice)
                        .build(),

                Transaction.builder()
                        .amount(850.0)
                        .transactionType(EntryType.EXPENSE)
                        .category(rent)
                        .date(LocalDateTime.now().minusDays(1))
                        .account(bob)
                        .build(),

                Transaction.builder()
                        .amount(150.0)
                        .transactionType(EntryType.EXPENSE)
                        .category(travel)
                        .date(LocalDateTime.now().minusDays(2))
                        .account(bob)
                        .build()
        ));
    }
}

