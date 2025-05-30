package pk.edu.budget_app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pk.edu.budget_app.domain.Category;
import pk.edu.budget_app.domain.User;
import pk.edu.budget_app.dto.CategoryDto;
import pk.edu.budget_app.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService sut;

    private final User user = User.builder()
            .id(1L)
            .name("john")
            .build();

    @Test
    void addCategory_shouldSaveNewCategory_whenNameNotExists() {
        // given
        var dto = CategoryDto.builder()
                .name("Groceries")
                .build();

        when(categoryRepository.findByUserAndName(eq(user), eq("Groceries")))
                .thenReturn(Optional.empty());

        var savedCategory = Category.builder()
                .id(10L)
                .name("Groceries")
                .user(user)
                .build();

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // when
        var result = sut.addCategory(dto, user);

        // then
        verify(categoryRepository).save(any(Category.class));
        assertThat(result.getName()).isEqualTo("Groceries");
        assertThat(result.getUser()).isSameAs(user);
    }

    @Test
    void addCategory_shouldThrowException_whenNameAlreadyExists() {
        // given
        var dto = CategoryDto.builder()
                .name("Groceries")
                .build();

        var existingCategory = Category.builder()
                .id(10L)
                .name("Groceries")
                .user(user)
                .build();

        when(categoryRepository.findByUserAndName(eq(user), eq("Groceries")))
                .thenReturn(Optional.of(existingCategory));

        // when + then
        assertThatThrownBy(() -> sut.addCategory(dto, user))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void getAllForUser_shouldReturnCategoryList() {
        // given
        var categories = List.of(
                Category.builder().id(1L).name("Food").user(user).build(),
                Category.builder().id(2L).name("Travel").user(user).build()
        );

        when(categoryRepository.findAllByUser(eq(user))).thenReturn(categories);

        // when
        var result = sut.getAllForUser(user);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Food");
        assertThat(result.get(1).getName()).isEqualTo("Travel");
    }

    @Test
    void getByUserAndName_shouldReturnCategoryIfExists() {
        // given
        var category = Category.builder()
                .id(1L)
                .name("Food")
                .user(user)
                .build();

        when(categoryRepository.findByUserAndName(eq(user), eq("Food")))
                .thenReturn(Optional.of(category));

        // when
        var result = sut.getByUserAndName(user, "Food");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Food");
    }
}
