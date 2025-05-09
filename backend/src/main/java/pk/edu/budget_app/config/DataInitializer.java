package pk.edu.budget_app.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import pk.edu.budget_app.domain.*;
import pk.edu.budget_app.repository.*;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

import static pk.edu.budget_app.util.DateUtils.now;

// initial data loading for development purposes
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @PostConstruct
    public void initData() {
        //Clear database
        transactionRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();

        // Create users
        User alice = userRepository.save(User.builder()
                .name("Alice")
                .build());

        User bob = userRepository.save(User.builder()
                .name("Bob")
                .build());

        // Create categories
        Category food = categoryRepository.save(Category.builder().name("Food").user(alice).build());
        Category salaryAlice = categoryRepository.save(Category.builder().name("Salary").user(alice).build());
        Category salaryBob = categoryRepository.save(Category.builder().name("Salary").user(bob).build());
        Category travel = categoryRepository.save(Category.builder().name("Travel").user(bob).build());

        // Create transactions
        transactionRepository.saveAll(List.of(
                Transaction.builder()
                        .amount(BigDecimal.valueOf(2000.0))
                        .transactionType(EntryType.INCOME)
                        .category(salaryAlice)
                        .date(now().minusDays(5))
                        .user(alice)
                        .build(),

                Transaction.builder()
                        .amount(BigDecimal.valueOf(120.0))
                        .transactionType(EntryType.EXPENSE)
                        .category(food)
                        .date(now().minusDays(3))
                        .user(alice)
                        .build(),

                Transaction.builder()
                        .amount(BigDecimal.valueOf(2000.0))
                        .transactionType(EntryType.INCOME)
                        .category(salaryBob)
                        .date(now().minusDays(5))
                        .user(bob)
                        .build(),

                Transaction.builder()
                        .amount(BigDecimal.valueOf(850.0))
                        .transactionType(EntryType.EXPENSE)
                        .category(travel)
                        .date(now().minusDays(1))
                        .user(bob)
                        .build(),

                Transaction.builder()
                        .amount(BigDecimal.valueOf(150.0))
                        .transactionType(EntryType.EXPENSE)
                        .category(travel)
                        .date(now().minusDays(2))
                        .user(bob)
                        .build(),

                Transaction.builder()
                        .amount(BigDecimal.valueOf(100.0))
                        .transactionType(EntryType.EXPENSE)
                        .category(travel)
                        .date(now().minusMonths(1))
                        .user(bob)
                        .build(),

                Transaction.builder()
                        .amount(BigDecimal.valueOf(100.0))
                        .transactionType(EntryType.EXPENSE)
                        .date(now().minusMonths(1))
                        .user(bob)
                        .build()
        ));
    }
}

