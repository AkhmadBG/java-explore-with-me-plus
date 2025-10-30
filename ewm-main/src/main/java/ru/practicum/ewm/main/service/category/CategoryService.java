package ru.practicum.ewm.main.service.category;

import org.springframework.data.domain.Page;
import ru.practicum.ewm.main.dto.category.CategoryDto;

public interface CategoryService {
    CategoryDto createOrUpdate(CategoryDto categoryDto);

    void delete(Long id);

    Page<CategoryDto> getAll(int from, int size);

    CategoryDto getById(Long id);
}
