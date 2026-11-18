package ru.vsu.cs.ui;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleHelper {
    private final Scanner scanner = new Scanner(
            System.in, StandardCharsets.UTF_8
    );

    public void printLine(String text) {
        System.out.println(text);
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public long readLong(String prompt) {
        while (true) {
            String value = readString(prompt);

            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                printLine("Введите целое число, например 1.");
            }
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            String value = readString(prompt);

            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                printLine(
                        "Введите существующую дату в формате ГГГГ-ММ-ДД,"
                                + " например 2026-11-15."
                );
            }
        }
    }
}