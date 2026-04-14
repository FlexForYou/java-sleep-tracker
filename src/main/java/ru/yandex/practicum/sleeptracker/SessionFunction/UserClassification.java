package ru.yandex.practicum.sleeptracker.SessionFunction;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

import static ru.yandex.practicum.sleeptracker.ErrorLog.writeLogError;

public class UserClassification implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        String typeUser = null;

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


        Long countOwl = sessions.stream()
                .filter(SleepingSession::checkForOwl)
                .count();
        Long countLark = sessions.stream()
                .filter(SleepingSession::checkForLark)
                .count();
        if (countOwl > countLark) {
            typeUser = "Сова";
        } else if (countOwl < countLark) {
            typeUser = "Жаворонок";
        } else if (countOwl == countLark) {
            typeUser = "Голубь";
        }


        return new SleepAnalysisResult("Ваш хронотип", typeUser);

    }

}