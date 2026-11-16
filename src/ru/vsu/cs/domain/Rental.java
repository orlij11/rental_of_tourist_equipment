package ru.vsu.cs.domain;

import ru.vsu.cs.exception.AppException;
import ru.vsu.cs.exception.ErrorCode;

import java.time.LocalDate;

public class Rental {
    private final long id;
    private final Client client;
    private final Equipment equipment;
    private final LocalDate issueDate;
    private final LocalDate plannedReturnDate;

    private LocalDate actualReturnDate;
    private RentalStatus status;

    public Rental(long id, Client client, Equipment equipment,
                  LocalDate issueDate, LocalDate plannedReturnDate) {
        if (id <= 0) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "ID выдачи должен быть положительным"
            );
        }

        if (client == null || equipment == null) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Укажите клиента и снаряжение"
            );
        }

        if (issueDate == null || plannedReturnDate == null) {
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

        if (plannedReturnDate.isBefore(issueDate)) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Плановая дата возврата не может быть раньше даты выдачи"
            );
        }

        this.id = id;
        this.client = client;
        this.equipment = equipment;
        this.issueDate = issueDate;
        this.plannedReturnDate = plannedReturnDate;
        this.status = RentalStatus.ACTIVE;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void markAsReturned(LocalDate returnDate) {
        if (status == RentalStatus.RETURNED) {
            throw new AppException(
                    ErrorCode.BUSINESS_RULE,
                    "По этой выдаче снаряжение уже возвращено"
            );
        }

        if (returnDate == null) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Укажите фактическую дату возврата"
            );
        }

        if (returnDate.isBefore(issueDate)) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Дата возврата не может быть раньше даты выдачи"
            );
        }

        if (returnDate.isAfter(LocalDate.now())) {
            throw new AppException(
                    ErrorCode.VALIDATION,
                    "Фактическая дата возврата не может быть в будущем"
            );
        }

        this.actualReturnDate = returnDate;
        this.status = RentalStatus.RETURNED;
    }
}