package ru.practicum.ewm.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

//    Public: Подборки событий Публичный API для работы с подборками событий
//    GET /compilations Получение подборок событий
//    GET /compilations/{compId} Получение подборки событий по его id

    @GetMapping
    public List<CompilationDto> getCompilations() {
        List<CompilationDto> compilationsDto = compilationService.getCompilations();
        return compilationsDto;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable("compId") Long compId) {
        CompilationDto compilationDto = compilationService.getCompilationById(compId);
        return compilationDto;
    }

}