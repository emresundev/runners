package dev.emresun.runners.run;

import java.time.LocalDateTime;

public record Run(
        Integer id,
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Integer distance,
        Location location
) { }
