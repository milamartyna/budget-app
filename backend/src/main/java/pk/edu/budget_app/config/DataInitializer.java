package pk.edu.budget_app.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import pk.edu.budget_app.domain.*;
import pk.edu.budget_app.dto.TransactionDto;
import pk.edu.budget_app.dto.UserLoginDto;
import pk.edu.budget_app.repository.*;
import org.springframework.context.annotation.Configuration;
import pk.edu.budget_app.service.TransactionService;
import pk.edu.budget_app.service.UserService;

import java.math.BigDecimal;
import java.util.List;

import static pk.edu.budget_app.util.DateUtils.now;

// initial data loading for development purposes
// comment out when running jacoco test report
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final UserService userService;
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
        User alice = userService.createUser(UserLoginDto.builder().name("Alice").password("a123").build());
        User bob = userService.createUser(UserLoginDto.builder().name("Bob").password("b123").build());

        // Create categories for Alice
        categoryRepository.save(Category.builder().name("Food").user(alice).build());
        categoryRepository.save(Category.builder().name("Salary").user(alice).build());
        categoryRepository.save(Category.builder().name("Entertainment").user(alice).build());

        // Create categories for Bob
        categoryRepository.save(Category.builder().name("Salary").user(bob).build());
        categoryRepository.save(Category.builder().name("Travel").user(bob).build());
        categoryRepository.save(Category.builder().name("Utilities").user(bob).build());

        // Create transactions
        var transactions = List.of(
                // Alice's transactions
                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(2500.0))
                        .transactionType(EntryType.INCOME)
                        .categoryName("Salary")
                        .accountName(alice.getName())
                        .date(now().minusDays(10))
                        .description("Monthly salary payment")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(85.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Food")
                        .accountName(alice.getName())
                        .date(now().minusDays(6))
                        .description("Grocery shopping at supermarket")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(45.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Entertainment")
                        .accountName(alice.getName())
                        .date(now().minusDays(4))
                        .description("Movie tickets with friends")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(60.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Food")
                        .accountName(alice.getName())
                        .date(now().minusDays(3))
                        .description("Dinner at Italian restaurant")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(15.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Entertainment")
                        .accountName(alice.getName())
                        .date(now().minusDays(1))
                        .description("Netflix monthly subscription")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(40.0))
                        .transactionType(EntryType.EXPENSE)
                        .accountName(alice.getName())
                        .date(now().minusDays(2))
                        .description("Gym membership fee")
                        .build(),

                // Bob's transactions
                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(3000.0))
                        .transactionType(EntryType.INCOME)
                        .categoryName("Salary")
                        .accountName(bob.getName())
                        .date(now().minusDays(15))
                        .description("Monthly salary from Tech Corp")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(220.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Travel")
                        .accountName(bob.getName())
                        .date(now().minusDays(7))
                        .description("Weekend trip hotel booking")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(90.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Utilities")
                        .accountName(bob.getName())
                        .date(now().minusDays(5))
                        .description("Electricity bill payment")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(60.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Travel")
                        .accountName(bob.getName())
                        .date(now().minusDays(2))
                        .description("Gas fill-up for car")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(50.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Utilities")
                        .accountName(bob.getName())
                        .date(now().minusDays(3))
                        .description("Water bill payment")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(130.0))
                        .transactionType(EntryType.EXPENSE)
                        .categoryName("Travel")
                        .accountName(bob.getName())
                        .date(now().minusDays(4))
                        .description("Flight ticket for business trip")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(300.0))
                        .transactionType(EntryType.INCOME)
                        .accountName(bob.getName())
                        .date(now().minusDays(4))
                        .description("Return on investments")
                        .build(),

                TransactionDto.builder()
                        .amount(BigDecimal.valueOf(40.0))
                        .transactionType(EntryType.EXPENSE)
                        .accountName(bob.getName())
                        .date(now().minusDays(5))
                        .description("Loan for a friend")
                        .build()
        );

        transactions.forEach(transactionService::saveAndCharge);
    }

}
