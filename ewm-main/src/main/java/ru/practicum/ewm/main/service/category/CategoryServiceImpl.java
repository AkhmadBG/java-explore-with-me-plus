package ru.practicum.ewm.main.service.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.mapper.CategoryMapper;
import ru.practicum.ewm.main.model.category.Category;
import ru.practicum.ewm.main.repository.category.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createOrUpdate(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size,
                Sort.by(Sort.Direction.DESC, "id"));
        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.getContent().stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id=" + id + " was not found"));

        return CategoryMapper.toCategoryDto(category);
    }
}
