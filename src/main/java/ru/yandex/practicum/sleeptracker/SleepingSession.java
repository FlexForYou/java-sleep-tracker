package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SleepingSession {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private SleepQuality sleepQuality;


    public SleepingSession(LocalDateTime startDateTime, LocalDateTime endDateTime, SleepQuality sleepQuality) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.sleepQuality = sleepQuality;
    }

    //01.10.25 22:15;02.10.25 08:00;GOOD
    public LocalDateTime getStartDateTime() {
        return startDateTime;               //01.10.25 22:15
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime; //02.10.25 08:00
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;               //GOOD
    }

    public Long getDurationMinutes() {
        return Duration.between(startDateTime, endDateTime).toMinutes();
    }

    public boolean checkSleeplessNights() {
        LocalTime nightStart = LocalTime.of(23, 59);
        LocalTime nightEnd = LocalTime.of(6, 0);

        // Сессия начинается до начала ночи и заканчивается во время ночи
        if ((startDateTime.toLocalTime().isBefore(nightStart) && endDateTime.toLocalTime().isAfter(nightStart)) ||
                // Сессия полностью внутри ночи
                ((startDateTime.toLocalTime().isAfter(nightStart) || startDateTime.toLocalTime().equals(nightStart)) &&
                        (endDateTime.toLocalTime().isBefore(nightEnd) || endDateTime.toLocalTime().equals(nightEnd))) ||
                // Сессия начинается во время ночи и заканчивается после
                ((startDateTime.toLocalTime().isAfter(nightStart) || startDateTime.toLocalTime().equals(nightStart)) &&
                        startDateTime.toLocalTime().isBefore(nightEnd) && endDateTime.toLocalTime().isAfter(nightEnd)) ||
                // Сессия охватывает всю ночь целиком
                (startDateTime.isBefore(startDateTime.toLocalDate().atTime(nightStart)) && endDateTime
                        .isAfter(endDateTime.toLocalDate().atTime(nightEnd)))) {

            return false;

        } else {
            return true;
        }

    }

    public boolean checkForLark() {
        LocalTime nightStart = LocalTime.of(22, 0);
        LocalTime nightEnd = LocalTime.of(7, 0);
        if ((startDateTime.toLocalTime().isBefore(nightStart) || startDateTime.toLocalTime().equals(nightStart))
                && (endDateTime.toLocalTime().isBefore(nightStart) || endDateTime.toLocalTime().equals(nightStart))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkForOwl() {
        LocalTime nightStart = LocalTime.of(23, 0);
        LocalTime nightEnd = LocalTime.of(9, 0);
        if ((startDateTime.toLocalTime().isAfter(nightStart) || startDateTime.toLocalTime().equals(nightStart))
                && (endDateTime.toLocalTime().isBefore(nightStart) || endDateTime.toLocalTime().equals(nightStart))) {
            return true;
        } else {
            return false;
        }
    }
}
