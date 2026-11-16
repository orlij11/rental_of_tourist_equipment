package ru.vsu.cs.validation;

import ru.vsu.cs.exception.AppException;
import ru.vsu.cs.exception.ErrorCode;

import java.time.LocalDate;

public class InputValidator {

    public void validateClient(String fullName, String phone) {
        if (fullName == null || fullName.isBlank()) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "ФИО клиента не должно быть пустым"
            );
        }

        if (phone == null || phone.isBlank()) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Телефон клиента не должен быть пустым"
            );
        }
    }

    public void validateEquipment(String name, String inventoryNumber) {
        if (name == null || name.isBlank()) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Название снаряжения не должно быть пустым"
            );
        }

        if (inventoryNumber == null || inventoryNumber.isBlank()) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Инвентарный номер не должен быть пустым"
            );
        }
    }

    public void validateRentalDates(LocalDate issueDate, LocalDate returnDate) {
        if (issueDate == null || returnDate == null) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Укажите даты выдачи и возврата"
            );
        }

        if (issueDate.isAfter(LocalDate.now())) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Дата выдачи не может быть в будущем"
            );
        }

        if (returnDate.isBefore(issueDate)) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Дата возврата не может быть раньше даты выдачи"
            );
        }
    }
}