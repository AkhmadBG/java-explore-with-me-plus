package ru.practicum.ewm.main.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class NewCompilationDto {

    @NotBlank
    @Size(min = 1, max = 50)
    private String title; // Заголовок подборки

    @NotNull
    private Boolean pinned; // Закреплена ли подборка на главной странице сайта

    @NotNull
    private Set<Long> events; // Список идентификаторов событий входящих в подборку

}