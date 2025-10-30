package ru.practicum.ewm.main.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.service.category.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.createOrUpdate(categoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void delete(Long id) {
        categoryService.delete(id);
    }

    @PatchMapping("/categories/{catId}")
    public CategoryDto update(Long id, @Valid @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        return categoryService.createOrUpdate(categoryDto);
    }
}
