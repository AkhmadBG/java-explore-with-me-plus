package ru.practicum.ewm.main.compilations.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.compilations.dto.CompilationDto;
import ru.practicum.ewm.main.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.main.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.main.compilations.service.CompilationService;

import java.util.List;

@Service
public class CompilationServiceImpl implements CompilationService {

    @Override
    public List<CompilationDto> getCompilations() {
        return List.of();
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return null;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilationById(Long compId) {

    }

    @Override
    public CompilationDto updateCompilationById(UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }

}
