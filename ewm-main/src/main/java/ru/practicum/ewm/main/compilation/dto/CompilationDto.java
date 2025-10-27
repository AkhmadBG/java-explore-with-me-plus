package ru.practicum.ewm.main.compilation.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.main.event.entity.Event;

import java.util.List;

@Data
@Builder
public class CompilationDto {

    private Long id;

    private String title; // Заголовок подборки

    private Boolean pinned; // Закреплена ли подборка на главной странице сайта

    private List<Event> events; // Список идентификаторов событий входящих в подборку

}
