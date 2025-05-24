package pk.edu.budget_app.dto;

import pk.edu.budget_app.domain.Category;
import pk.edu.budget_app.domain.User;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
            .name(category.getName())
            .build();
    }

    public static Category toEntity(CategoryDto dto, User user) {
        return Category.builder()
            .name(dto.getName())
            .user(user)
            .build();
    }
}
