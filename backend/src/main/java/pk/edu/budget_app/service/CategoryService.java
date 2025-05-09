package pk.edu.budget_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pk.edu.budget_app.domain.Category;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.repository.CategoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<Category> getByUserAndName(User user, String name) {
        return categoryRepository.findByUserAndName(user, name);
    }
}
