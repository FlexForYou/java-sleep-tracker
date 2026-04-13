package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.sleeptracker.ErrorLog.writeLogError;

public class SleepingSessionLoader {


    public static List<SleepingSession> createListSleepingSession(String filename) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            return br.lines()
                    .filter(line -> !line.trim().isEmpty()) // Игнорируем пустые строки
                    .map(line -> {
                        try {
                            String[] parts = line.split(";");
                            if (parts.length != 3) {
                                writeLogError("Некорректный формат строки: " + line + "\n");
                                return null;
                            }

                            LocalDateTime startDateTime = LocalDateTime.parse(parts[0], formatter);
                            LocalDateTime endDateTime = LocalDateTime.parse(parts[1], formatter);
                            SleepQuality sleepQuality = SleepQuality.valueOf(parts[2]);

                            return new SleepingSession(startDateTime, endDateTime, sleepQuality);
                        } catch (Exception e) {
                            writeLogError("Ошибка обработки строки '" + line + "': " + e.getMessage() + "\n");
                            return null;
                        }
                    })
                    .filter(session -> session != null) // Убираем null-записи (ошибочные строки)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            writeLogError("Произошла ошибка во время чтения файла " + filename + ": " + e.getMessage() + "\n");
            throw e;
        }
    }
}








