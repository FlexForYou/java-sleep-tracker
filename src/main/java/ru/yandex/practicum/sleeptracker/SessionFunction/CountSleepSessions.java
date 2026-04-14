package ru.yandex.practicum.sleeptracker.SessionFunction;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

import static ru.yandex.practicum.sleeptracker.ErrorLog.writeLogError;

public class CountSleepSessions implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions == null) {
            String errorMessage = "Ошибка: передан null вместо списка сессий сна." + "\n";
            writeLogError(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (sessions.isEmpty()) {
            String errorMessage = "Ошибка: список сессий сна пуст. Анализ невозможен." + "\n";
            writeLogError(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }


        Long count = sessions.stream()
                .count();

        return new SleepAnalysisResult("Текущее количество сессий сна", count);
    }
}


