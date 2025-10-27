package ru.practicum.ewm.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.main.compilation.service.CompilationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

//    Admin: Подборки событий API для работы с подборками событий
//    POST /admin/compilations Добавление новой подборки (подборка может не содержать событий)
//    DELETE /admin/compilations/{compId} Удаление подборки
//    PATCH /admin/compilations/{compId} Обновить информацию о подборке

    @PostMapping
    public CompilationDto createCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        CompilationDto compilationDto = compilationService.createCompilation(newCompilationDto);
        return compilationDto;
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilationById(@PathVariable("compId") Long compId) {
        compilationService.deleteCompilationById(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilationById(@PathVariable("compId") Long compId, @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        CompilationDto compilationDto = compilationService.updateCompilationById(updateCompilationRequest);
        return compilationDto;
    }

}