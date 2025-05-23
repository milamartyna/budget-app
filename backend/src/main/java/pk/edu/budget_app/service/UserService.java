package pk.edu.budget_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.dto.UserLoginDto;
import pk.edu.budget_app.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserLoginDto dto) {
        if (userRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalStateException("Username already exists: " + dto.getName());
        }

        var user = User.builder()
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User getUserOrThrow(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + name));
    }

    public void addExpense(User user, BigDecimal amount) {
        user.setTotalExpense(user.getTotalExpense().add(amount));
        userRepository.save(user);
    }

    public void addIncome(User user, BigDecimal amount) {
        user.setTotalIncome(user.getTotalIncome().add(amount));
        userRepository.save(user);
    }
}
