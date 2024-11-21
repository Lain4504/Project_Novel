package com.backend.postservice.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class DateTimeFormatter {
    Map<Long, Function<Instant, String>> strategyMap = new LinkedHashMap<>();
    public DateTimeFormatter() {
        strategyMap.put(60L, this::formatInSeconds);
        strategyMap.put(3600L, this::formatInMinutes);
        strategyMap.put(86400L, this::formatInHours);
        strategyMap.put(Long.MAX_VALUE, this::formatInDays);
    }
    private String formatInSeconds(Instant instant) {
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());
            return elapseSeconds + " seconds";
    }
    private String formatInMinutes(Instant instant) {
        long elapseSeconds = ChronoUnit.MINUTES.between(instant, Instant.now());
        return elapseSeconds + " minutes";
    }
    private String formatInHours(Instant instant) {
        long elapseSeconds = ChronoUnit.HOURS.between(instant, Instant.now());
        return elapseSeconds + " hours";
    }
    private String formatInDays(Instant instant) {
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ISO_DATE;
        return localDateTime.format(dateTimeFormatter);
    }

    public String format(Instant instant) {
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());
        var strategy = strategyMap.entrySet()
                .stream()
                .filter(entry -> elapseSeconds < entry.getKey())
                .findFirst().get();
        return strategy.getValue().apply(instant);
    }
}
