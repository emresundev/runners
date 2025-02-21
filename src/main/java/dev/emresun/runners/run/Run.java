package dev.emresun.runners.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Run(
        Integer id,
        @NotEmpty
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        @Positive
        Integer distance,
        Location location
) {
    public Run {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }


}
