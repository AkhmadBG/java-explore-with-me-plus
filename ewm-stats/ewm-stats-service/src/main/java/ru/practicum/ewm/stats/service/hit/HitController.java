package ru.practicum.ewm.stats.service.hit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.dto.CreateHitDto;
import ru.practicum.ewm.stats.dto.GetStatsDto;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.stats.dto.ViewStats;
import ru.practicum.ewm.stats.service.hit.service.HitService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class HitController {
    private final HitService hitService;

    @PostMapping(path = "/users")
    public HitDto createUser(@RequestBody @Valid CreateHitDto hitCreateDto) {
        return hitService.create(hitCreateDto);
    }

    @PostMapping(path = "/hit")
    public HitDto create(@RequestBody @Valid CreateHitDto hitCreateDto) {
        return hitService.create(hitCreateDto);
    }

    @GetMapping(path = "/stats")
    public List<ViewStats> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        GetStatsDto getStatsDto = GetStatsDto.builder()
                .start(start)
                .end(end)
                .uris(uris)
                .unique(unique)
                .build();

        return hitService.getStats(getStatsDto);
    }
}
