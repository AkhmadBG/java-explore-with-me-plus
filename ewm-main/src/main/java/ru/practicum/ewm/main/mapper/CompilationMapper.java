package ru.practicum.ewm.main.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.main.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.dto.event.EventFullDto;
import ru.practicum.ewm.main.entity.Compilation;
import ru.practicum.ewm.main.entity.Event;

import java.util.List;

@Component
public class CompilationMapper {

    public CompilationDto toCompilationDto(Compilation compilation) {
        List<EventFullDto> events = compilation.getEvents().stream()
                .map(EventMapper::toEventFullDto)
                .toList();
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(events)
                .build();
    }

    public Compilation toCompilation(NewCompilationDto newCompilationDto,
                                            List<Event> events) {
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .events(events)
                .build();
    }

    public void updateCompilation(Compilation compilation,
                                                UpdateCompilationRequest updateCompilationRequest,
                                                List<Event> events) {
        if (updateCompilationRequest.hasTitle()) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.hasPinned()) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.hasEvents()) {
            compilation.setEvents(events);
        }
    }

}
