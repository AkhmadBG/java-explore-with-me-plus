package ru.practicum.ewm.main.dto.compilation;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.main.entity.Event;

import java.util.List;

@Data
@Builder
public class CompilationDto {

    private Long id;

    private String title;

    private Boolean pinned;

    private List<Event> events;

}
