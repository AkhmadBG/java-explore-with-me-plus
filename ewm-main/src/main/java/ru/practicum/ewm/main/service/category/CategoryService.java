package ru.practicum.ewm.main.service.category;

import ru.practicum.ewm.main.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createOrUpdate(CategoryDto categoryDto);

    void delete(Long id);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(Long id);
}
