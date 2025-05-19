package pk.edu.budget_app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.repository.UserRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService sut;

    @Test
    void addExpense_shouldIncreaseTotalExpense_andPersist() {
        // given
        var user = User.builder()
                .id(1L)
                .name("john")
                .totalIncome(BigDecimal.ZERO)
                .totalExpense(BigDecimal.ZERO)
                .build();

        var amount = BigDecimal.valueOf(50.25);

        // when
        sut.addExpense(user, amount);

        // then
        var captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        var persisted = captor.getValue();
        assertThat(persisted.getTotalExpense()).isEqualByComparingTo(BigDecimal.valueOf(50.25));
        assertThat(persisted.getTotalIncome()).isEqualByComparingTo(BigDecimal.ZERO);
    }


    @Test
    void addIncome_shouldIncreaseTotalIncome_andPersist() {
        // given
        var user = User.builder()
                .id(2L)
                .name("alice")
                .totalIncome(BigDecimal.valueOf(100))
                .totalExpense(BigDecimal.valueOf(20))
                .build();

        var amount = BigDecimal.valueOf(33.00);

        // when
        sut.addIncome(user, amount);

        // then
        var captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        var persisted = captor.getValue();
        assertThat(persisted.getTotalIncome()).isEqualByComparingTo(BigDecimal.valueOf(133));
        assertThat(persisted.getTotalExpense()).isEqualByComparingTo(BigDecimal.valueOf(20));
    }
}
