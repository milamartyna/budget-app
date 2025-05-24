package pk.edu.budget_app.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.edu.budget_app.dto.CategoryDto;
import pk.edu.budget_app.dto.CategoryMapper;
import pk.edu.budget_app.service.UserService;
import pk.edu.budget_app.service.CategoryService;

import java.util.List;
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/{userName}")
    public List<CategoryDto> getCategories(@PathVariable String userName) {
        var user = userService.getUserOrThrow(userName);
        return categoryService.getAllForUser(user)
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    @PostMapping("/{userName}")
    public ResponseEntity<CategoryDto> addCategory(
        @PathVariable String userName,
        @RequestBody CategoryDto dto
    ) {
        var user = userService.getUserOrThrow(userName);
        var saved = categoryService.addCategory(dto, user);
        return ResponseEntity.ok(CategoryMapper.toDto(saved));
    }
}
