package ru.practicum.ewm.main.util;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.main.enums.EventState;
import ru.practicum.ewm.main.enums.SortValue;

import java.util.List;

@UtilityClass
public class SearchValidators {
    public static boolean hasUsers(List<Long> users) {
        return users != null && !users.isEmpty();
    }

    private static boolean hasCategories(List<Long> categories) {
        return categories != null && !categories.isEmpty();
    }

    private static boolean hasDateRange(String start, String end) {
        return start != null || end != null;
    }

    public static boolean hasText(String text) {
        return text != null && !text.isBlank();
    }

    public static boolean hasStates(EventState states) {
        return states != null;
    }

    public static boolean shouldSort(SortValue sort) {
        return sort != null;
    }

    public static boolean isOnlyAvailable(Boolean onlyAvailable) {
        return Boolean.TRUE.equals(onlyAvailable);
    }
}
