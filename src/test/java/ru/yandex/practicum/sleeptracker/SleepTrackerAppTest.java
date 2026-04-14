package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SessionFunction.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SleepTrackerAppTest {

    //  нормальный ночной сон
    SleepingSession sleepingSession1 = new SleepingSession(
            LocalDateTime.of(2025, 10, 1, 22, 15), // 01.10.25 22:15
            LocalDateTime.of(2025, 10, 2, 8, 0),   // 02.10.25 08:00
            SleepQuality.GOOD
    );
    //  короткий дневной сон (Это плохой сон для testLotSessionsWithBadSleep() )
    SleepingSession sleepingSession2 = new SleepingSession(
            LocalDateTime.of(2025, 10, 1, 14, 30), // 01.10.25 14:30
            LocalDateTime.of(2025, 10, 1, 15, 45), // 01.10.25 15:45
            SleepQuality.NORMAL
    );
    //  сон с вечера до ночи
    SleepingSession sleepingSession3 = new SleepingSession(
            LocalDateTime.of(2025, 10, 2, 18, 45), // 02.10.25 23:45
            LocalDateTime.of(2025, 10, 3, 3, 15),  // 03.10.25 07:15
            SleepQuality.BAD
    );
    //  сон с ночи до дня
    SleepingSession sleepingSession4 = new SleepingSession(
            LocalDateTime.of(2025, 10, 2, 3, 45), // 02.10.25 23:45
            LocalDateTime.of(2025, 10, 3, 12, 15),  // 03.10.25 07:15
            SleepQuality.NORMAL
    );
    List<SleepingSession> sleepingSessionsList = List.of(sleepingSession1, sleepingSession2, sleepingSession3, sleepingSession4);

    @Test
    void testSleeplessNights() {
        SleeplessNights testFun = new SleeplessNights();
        SleepAnalysisResult result = testFun.apply(sleepingSessionsList);

        assertEquals("Количество бессонных ночей", result.getDescription());
        assertEquals(1L, result.getResult());

    }


    @Test
    void testCountSleepSessions() {
        CountSleepSessions testFun = new CountSleepSessions();
        SleepAnalysisResult result = testFun.apply(sleepingSessionsList);

        assertEquals("Текущее количество сессий сна", result.getDescription());
        assertEquals(4L, result.getResult());

    }

    @Test
    void testLotSessionsWithBadSleep() {
        LotSessionsWithBadSleep testFun = new LotSessionsWithBadSleep();
        SleepAnalysisResult result = testFun.apply(sleepingSessionsList);

        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
        assertEquals(1L, result.getResult());
    }

    @Test
    void testMaxSessionDuration() {
        List<SleepingSession> sleepingSessionsList = List.of(new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 3, 00), // 02.10.25 23:45
                        LocalDateTime.of(2025, 10, 2, 3, 15),  // 03.10.25 07:15
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 3, 00), // 02.10.25 23:45
                        LocalDateTime.of(2025, 10, 2, 3, 5),  // 03.10.25 07:15
                        SleepQuality.NORMAL
                )
        );

        MaxSessionDuration testFun = new MaxSessionDuration();
        SleepAnalysisResult result = testFun.apply(sleepingSessionsList);

        assertEquals("Максимальная продолжительность сессии (в минутах)", result.getDescription());
        assertEquals(15L, result.getResult());
    }

    @Test
    void testMinSessionDuration() {
        List<SleepingSession> sleepingSessionsList = List.of(new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 3, 00), // 02.10.25 23:45
                        LocalDateTime.of(2025, 10, 2, 3, 15),  // 03.10.25 07:15
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 3, 00), // 02.10.25 23:45
                        LocalDateTime.of(2025, 10, 2, 3, 5),  // 03.10.25 07:15
                        SleepQuality.NORMAL
                )
        );

        MinSessionDuration testFun = new MinSessionDuration();
        SleepAnalysisResult result = testFun.apply(sleepingSessionsList);

        assertEquals("Минимальная продолжительность сессии (в минутах)", result.getDescription());
        assertEquals(5L, result.getResult());
    }

    @Test
    void testUserСlassificationForOwl() {
        List<SleepingSession> sleepingSessionsList = List.of(new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 23, 00), // 02.10.25 23:45
                LocalDateTime.of(2025, 10, 3, 9, 00),  // 03.10.25 07:15
                SleepQuality.NORMAL
        ));

        UserClassification testFun = new UserClassification();
        SleepAnalysisResult result = testFun.apply(sleepingSessionsList);

        assertEquals("Ваш хронотип", result.getDescription());
        assertEquals("Сова", result.getResult());
    }

    @Test
    void testUserСlassificationForLark() {
        List<SleepingSession> sleepingSessionsList = List.of(new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 22, 00), // 02.10.25 23:45
                LocalDateTime.of(2025, 10, 3, 7, 00),  // 03.10.25 07:15
                SleepQuality.NORMAL
        ));

        UserClassification testFun = new UserClassification();
        SleepAnalysisResult result = testFun.apply(sleepingSessionsList);

        assertEquals("Ваш хронотип", result.getDescription());
        assertEquals("Жаворонок", result.getResult());
    }

    @Test
    void testUserСlassificationForPigeon() {
        List<SleepingSession> sleepingSessionsList = List.of(new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 00), // 02.10.25 23:45
                        LocalDateTime.of(2025, 10, 3, 9, 00),  // 03.10.25 07:15
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 00), // 02.10.25 23:45
                        LocalDateTime.of(2025, 10, 3, 7, 00),  // 03.10.25 07:15
                        SleepQuality.NORMAL
                ));

        UserClassification testFun = new UserClassification();
        SleepAnalysisResult result = testFun.apply(sleepingSessionsList);

        assertEquals("Ваш хронотип", result.getDescription());
        assertEquals("Голубь", result.getResult());
    }

}




