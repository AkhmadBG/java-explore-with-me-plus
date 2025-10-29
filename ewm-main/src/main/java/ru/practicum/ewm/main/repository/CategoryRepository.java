package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.main.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

