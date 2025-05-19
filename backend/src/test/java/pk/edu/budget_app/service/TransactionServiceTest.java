package pk.edu.budget_app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pk.edu.budget_app.domain.Category;
import pk.edu.budget_app.domain.EntryType;
import pk.edu.budget_app.domain.Transaction;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.dto.TransactionDto;
import pk.edu.budget_app.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserService userService;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    TransactionService sut;


    private final User user = User.builder()
            .id(1L)
            .name("john")
            .totalIncome(BigDecimal.ZERO)
            .totalExpense(BigDecimal.ZERO)
            .build();

    private final Category groceries = Category.builder()
            .id(10L)
            .name("Groceries")
            .user(user)
            .build();

    private final LocalDateTime NOW = LocalDateTime.of(2025, 5, 19, 10, 0);

    @Test
    void saveAndCharge_shouldPersistExpenseAndDebitUser() {
        // given
        var dto = TransactionDto.builder()
                .accountName("john")
                .amount(BigDecimal.valueOf(100))
                .transactionType(EntryType.EXPENSE)
                .categoryName("Groceries")
                .description("Pay day 🎉")
                .date(NOW)
                .build();

        var txCaptor = ArgumentCaptor.forClass(Transaction.class);

        when(userService.getUserOrThrow(eq("john"))).thenReturn(user);

        when(categoryService.getByUserAndName(eq(user), eq("Groceries")))
                .thenReturn(Optional.of(groceries));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(inv -> inv.getArgument(0));   // echo argument

        // when
        var saved = sut.saveAndCharge(dto);

        // then
        verify(userService).addExpense(user, BigDecimal.valueOf(100));
        verify(userService, never()).addIncome(any(), any());

        verify(transactionRepository).save(txCaptor.capture());
        var persisted = txCaptor.getValue();

        assertThat(persisted.getUser()).isSameAs(user);
        assertThat(persisted.getCategory()).isSameAs(groceries);
        assertThat(persisted.getTransactionType()).isEqualTo(EntryType.EXPENSE);
        assertThat(persisted.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void saveAndCharge_shouldPersistIncomeAndCreditUser_evenWithoutCategory() {
        // given
        var dto = TransactionDto.builder()
                .accountName("john")
                .amount(BigDecimal.valueOf(42.50))
                .transactionType(EntryType.INCOME)
                .description("Found money")
                .build();

        when(userService.getUserOrThrow(eq("john"))).thenReturn(user);

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // when
        var saved = sut.saveAndCharge(dto);

        // then
        verify(userService).addIncome(user, BigDecimal.valueOf(42.50));
        verify(userService, never()).addExpense(any(), any());
        verify(categoryService, never()).getByUserAndName(any(), anyString());

        assertThat(saved.getCategoryName()).isNull();
        assertThat(saved.getTransactionType()).isEqualTo(EntryType.INCOME);
    }

    @Test
    void getTransactionsByMonth_shouldDelegateWithCorrectDateRange() {
        var ym = YearMonth.of(2025, 1);                  // January 2025
        var from = ym.atDay(1).atStartOfDay();       // 2025-01-01T00:00
        var to = ym.atEndOfMonth().atTime(23, 59, 59, 999_999_999);

        sut.getTransactionsByMonth(user, ym);

        verify(transactionRepository)
                .findByUserAndDateBetween(eq(user), eq(from), eq(to));
    }

    @Test
    void getTransactionsByMonthAndType_shouldDelegateWithCorrectParameters() {
        var ym = YearMonth.of(2024, 12);                 // December 2024
        var from = ym.atDay(1).atStartOfDay();
        var to = ym.atEndOfMonth().atTime(23, 59, 59, 999_999_999);

        sut.getTransactionsByMonthAndType(user, ym, EntryType.EXPENSE);

        verify(transactionRepository)
                .findByUserAndTransactionTypeAndDateBetween(eq(user), eq(EntryType.EXPENSE),
                        eq(from), eq(to));
    }

}
