package ru.practicum.ewm.main.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.dto.category.CategoryDto;
import ru.practicum.ewm.main.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.dto.category.UpdateCategoryDto;
import ru.practicum.ewm.main.exception.ConflictException;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.mapper.CategoryMapper;
import ru.practicum.ewm.main.entity.Category;
import ru.practicum.ewm.main.repository.CategoryRepository;
import ru.practicum.ewm.main.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto createOrUpdate(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with id=" + id + " was not found");
        }

        if (eventRepository.existsByCategoryId(id)) {
            throw new ConflictException("Cannot delete category with id=" + id + " because it has linked events");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("name").descending());
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.getContent()
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id=" + id + " was not found"));

        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toCategory(newCategoryDto);
        Category newCategory = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id, UpdateCategoryDto updateCategoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id=" + id + " was not found"));

        if (updateCategoryDto.getName() != null &&
                !updateCategoryDto.getName().equals(category.getName())) {

            boolean nameExists = categoryRepository.existsByNameAndIdNot(updateCategoryDto.getName(), id);
            if (nameExists) {
                throw new ConflictException("Category name already exists");
            }
        }

        CategoryMapper.updateCategory(category, updateCategoryDto);
        Category updateCategory = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(updateCategory);
    }
}