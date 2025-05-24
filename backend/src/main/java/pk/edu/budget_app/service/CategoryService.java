package pk.edu.budget_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pk.edu.budget_app.domain.Category;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.dto.CategoryDto;
import pk.edu.budget_app.dto.CategoryMapper;
import pk.edu.budget_app.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<Category> getByUserAndName(User user, String name) {
        return categoryRepository.findByUserAndName(user, name);
    }
    public List<Category> getAllForUser(User user) {
        return categoryRepository.findAllByUser(user);
    }

    public Category addCategory(CategoryDto dto, User user) {
        return categoryRepository.save(CategoryMapper.toEntity(dto, user));
    }
}
