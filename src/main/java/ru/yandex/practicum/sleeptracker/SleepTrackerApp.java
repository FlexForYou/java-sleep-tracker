package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import ru.yandex.practicum.sleeptracker.SessionFunction.*;

public class SleepTrackerApp {

    public static void main(String[] args) throws IOException {

        SleepingSessionLoader sleepingSessionLoader = new SleepingSessionLoader();
        List<SleepingSession> listSleepingSession = sleepingSessionLoader
                .createListSleepingSession("src/main/resources/sleep_log.txt");

        List<Function<List<SleepingSession>, SleepAnalysisResult>> functionList = new ArrayList<>();

        functionList.add(new CountSleepSessions());
        functionList.add(new MaxSessionDuration());
        functionList.add(new MinSessionDuration());
        functionList.add(new MidSessionLength());
        functionList.add(new LotSessionsWithBadSleep());
        functionList.add(new SleeplessNights());
        functionList.add(new UserСlassification());

        System.out.println("=========================================================================================");
        functionList.stream()
                .map(function -> function.apply(listSleepingSession))
                .collect(Collectors.toList())
                .forEach(result -> System.out.println(result.toString()));
        System.out.println("=========================================================================================");

    }
}