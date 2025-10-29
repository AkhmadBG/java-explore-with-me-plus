package ru.practicum.ewm.main.mapper;

import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.entity.Category;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }
}
