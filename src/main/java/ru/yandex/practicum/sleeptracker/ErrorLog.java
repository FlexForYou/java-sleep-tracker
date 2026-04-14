package ru.yandex.practicum.sleeptracker;

import java.io.FileWriter;
import java.io.IOException;


public class ErrorLog {

    private static final String ERROR_LOG_FILE = "src/main/resources/error_log";

    public static void writeLogError(String errorMessage) {
        try (FileWriter writer = new FileWriter(ERROR_LOG_FILE, true)) {
            writer.write(java.time.LocalDateTime.now() + " - " + errorMessage + "\n");
        } catch (IOException e) {
            System.err.println("Не удалось записать в лог ошибок: " + e.getMessage());
        }
    }
}
