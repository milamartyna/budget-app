package pk.edu.budget_app.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import pk.edu.budget_app.domain.*;
import pk.edu.budget_app.dto.TransactionDto;
import pk.edu.budget_app.repository.*;
import org.springframework.context.annotation.Configuration;
import pk.edu.budget_app.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

import static pk.edu.budget_app.util.DateUtils.now;

// initial data loading for development purposes
// comment out when running jacoco test report
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    @PostConstruct
    public void initData() {
        // Clear database
        transactionRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();

        // Create users
        User alice = userRepository.save(User.builder().name("Alice").build());
        User bob = userRepository.save(User.builder().name("Bob").build());

        // Create categories
        categoryRepository.save(Category.builder().name("Food").user(alice).build());
        categoryRepository.save(Category.builder().name("Salary").user(alice).build());
        categoryRepository.save(Category.builder().name("Salary").user(bob).build());
        categoryRepository.save(Category.builder().name("Travel").user(bob).build());

        // Create transactions via DTO
        var transactions = List.of(
                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(2000.0))
                        .transactionType(EntryType.INCOME)
                        .categoryName("Salary")
                        .accountName(alice.getName())
                        .date(now().minusDays(5))
                        .description("Alice's salary")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(120.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Food")
                        .accountName(alice.getName())
                        .date(now().minusDays(3))
                        .description("Alice's food expense")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(2000.0))
                        .transactionType(EntryType.INCOME)
                        .categoryName("Salary")
                        .accountName(bob.getName())
                        .date(now().minusDays(5))
                        .description("Bob's salary")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(850.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Travel")
                        .accountName(bob.getName())
                        .date(now().minusDays(1))
                        .description("Bob's travel expense")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(150.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Travel")
                        .accountName(bob.getName())
                        .date(now().minusDays(2))
                        .description("Bob's travel expense")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(100.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Travel")
                        .accountName(bob.getName())
                        .date(now().minusMonths(1))
                        .description("Bob's travel expense last month")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(100.0))
                        .transactionType(EntryType.EXPENSE)
                        .accountName(bob.getName())
                        .date(now().minusMonths(1))
                        .description("Bob's uncategorized expense")
                        .build()
        );

        transactions.forEach(transactionService::saveAndCharge);
    }
}
