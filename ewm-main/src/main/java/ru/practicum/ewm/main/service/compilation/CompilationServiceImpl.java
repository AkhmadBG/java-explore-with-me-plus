package ru.practicum.ewm.main.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.entity.Compilation;
import ru.practicum.ewm.main.entity.Event;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.mapper.CompilationMapper;
import ru.practicum.ewm.main.repository.CompilationRepository;
import ru.practicum.ewm.main.service.event.EventServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventServiceImpl eventServiceImpl;
    private final CompilationMapper compilationMapper;

    @Override
    public Page<CompilationDto> getCompilations(int page, int size) {
        Page<Compilation> compilationsPage = compilationRepository.findAll(PageRequest.of(page, size));
        return compilationsPage.map(compilationMapper::toCompilationDto);
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository
                .findById(compId).orElseThrow(() -> new NotFoundException("подборка не найдена"));
        return compilationMapper.toCompilationDto(compilation);
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = new ArrayList<>();
        if (newCompilationDto.hasEvents()) {
            events = newCompilationDto.getEvents().stream()
                    .map(eventServiceImpl::getEventById)
                    .toList();
        }
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto, events);
        Compilation newCompilation = compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(newCompilation);
    }

    @Override
    public void deleteCompilationById(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilationById(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("подборка не найдена"));
        List<Event> events = new ArrayList<>();
        if (updateCompilationRequest.hasEvents()) {
            events = updateCompilationRequest.getEvents().stream()
                    .map(eventServiceImpl::getEventById)
                    .toList();
        }
        CompilationMapper.updateCompilation(compilation, updateCompilationRequest, events);
        Compilation updateCompilation = compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(updateCompilation);
    }

}