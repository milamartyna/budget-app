package pk.edu.budget_app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.dto.UserLoginDto;
import pk.edu.budget_app.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService sut;

    @Test
    void subtractExpense_shouldDecreaseTotalExpense_andPersist() {
        var user = User.builder()
                .id(1L)
                .name("john")
                .totalExpense(BigDecimal.valueOf(100))
                .build();

        sut.subtractExpense(user, BigDecimal.valueOf(40));

        verify(userRepository).save(user);
        assertThat(user.getTotalExpense()).isEqualByComparingTo(BigDecimal.valueOf(60));
    }

    @Test
    void subtractIncome_shouldDecreaseTotalIncome_andPersist() {
        var user = User.builder()
                .id(2L)
                .name("alice")
                .totalIncome(BigDecimal.valueOf(200))
                .build();

        sut.subtractIncome(user, BigDecimal.valueOf(50));

        verify(userRepository).save(user);
        assertThat(user.getTotalIncome()).isEqualByComparingTo(BigDecimal.valueOf(150));
    }

    @Test
    void createUser_shouldEncodePassword_andSaveUser() {
        var dto = UserLoginDto.builder()
                .name("bob")
                .password("secret")
                .build();

        when(userRepository.findByName("bob")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secret")).thenReturn("hashedSecret");

        var savedUser = User.builder().id(10L).name("bob").password("hashedSecret").build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var result = sut.createUser(dto);

        verify(passwordEncoder).encode("secret");
        verify(userRepository).save(any(User.class));

        assertThat(result.getName()).isEqualTo("bob");
        assertThat(result.getPassword()).isEqualTo("hashedSecret");
    }

    @Test
    void createUser_shouldThrowException_whenUsernameExists() {
        var dto = UserLoginDto.builder().name("bob").password("pass").build();

        when(userRepository.findByName("bob"))
                .thenReturn(Optional.of(User.builder().id(1L).name("bob").build()));

        assertThatThrownBy(() -> sut.createUser(dto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Username already exists");
    }

    @Test
    void getUserOrThrow_shouldReturnUser_ifExists() {
        var user = User.builder().id(1L).name("john").build();

        when(userRepository.findByName("john"))
                .thenReturn(Optional.of(user));

        var result = sut.getUserOrThrow("john");

        assertThat(result).isSameAs(user);
    }

    @Test
    void getUserOrThrow_shouldThrowException_ifNotFound() {
        when(userRepository.findByName("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.getUserOrThrow("ghost"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");
    }
}
