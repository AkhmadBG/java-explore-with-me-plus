package ru.practicum.ewm.main.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.enums.SortValue;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicEventSearchRequest {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private String rangeStart;
    private String rangeEnd;
    private Boolean onlyAvailable;
    private SortValue sort;
    private Integer from;
    private Integer size;

    public static PublicEventSearchRequest fromParams(
            String text,
            List<Long> categories,
            Boolean paid,
            String rangeStart,
            String rangeEnd,
            Boolean onlyAvailable,
            SortValue sort,
            Integer from,
            Integer size) {

        return PublicEventSearchRequest.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable != null ? onlyAvailable : false)
                .sort(sort)
                .from(from != null ? from : 0)
                .size(size != null ? size : 10)
                .build();
    }
}
