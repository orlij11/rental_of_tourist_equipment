package ru.vsu.cs.service;

import ru.vsu.cs.domain.Client;
import ru.vsu.cs.domain.Equipment;
import ru.vsu.cs.domain.Rental;
import ru.vsu.cs.domain.RentalStatus;
import ru.vsu.cs.exception.AppException;
import ru.vsu.cs.exception.ErrorCode;
import ru.vsu.cs.id.IdProvider;
import ru.vsu.cs.repository.RentalRepository;
import ru.vsu.cs.validation.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RentalService {
    private final RentalRepository rentalRepository;
    private final ClientService clientService;
    private final EquipmentService equipmentService;
    private final IdProvider idProvider;
    private final InputValidator validator;

    public RentalService(RentalRepository rentalRepository,
                         ClientService clientService,
                         EquipmentService equipmentService,
                         IdProvider idProvider,
                         InputValidator validator) {
        this.rentalRepository = rentalRepository;
        this.clientService = clientService;
        this.equipmentService = equipmentService;
        this.idProvider = idProvider;
        this.validator = validator;
    }

    public Rental createRental(long clientId, long equipmentId,
                               LocalDate issueDate, LocalDate plannedReturnDate) {
        validator.validateRentalDates(issueDate, plannedReturnDate);

        Client client = clientService.getClientById(clientId);
        Equipment equipment = equipmentService.getEquipmentById(equipmentId);

        LocalDate lastReturnDate = null;

        for (Rental previous : rentalRepository.findByEquipmentId(equipmentId)) {
            if (previous.getStatus() == RentalStatus.ACTIVE) {
                throw new AppException(
                        ErrorCode.BUSINESS_RULE,
                        "Снаряжение уже выдано и ещё не возвращено"
                );
            }

            LocalDate previousReturnDate = previous.getActualReturnDate();

            if (lastReturnDate == null
                    || previousReturnDate.isAfter(lastReturnDate)) {
                lastReturnDate = previousReturnDate;
            }
        }

        if (lastReturnDate != null && issueDate.isBefore(lastReturnDate)) {
            throw new AppException(
                    ErrorCode.BUSINESS_RULE,
                    "Дата новой выдачи должна быть не раньше " + lastReturnDate
            );
        }

        Rental rental = new Rental(
                idProvider.nextId(),
                client,
                equipment,
                issueDate,
                plannedReturnDate
        );

        rentalRepository.save(rental);
        return rental;
    }

    public Rental returnEquipment(long rentalId, LocalDate actualReturnDate) {
        Rental rental = getRentalById(rentalId);

        rental.markAsReturned(actualReturnDate);
        rentalRepository.save(rental);

        return rental;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(long id) {
        Optional<Rental> result = rentalRepository.findById(id);

        if (result.isEmpty()) {
            throw new AppException(
                    ErrorCode.NOT_FOUND,
                    "Выдача с ID " + id + " не найдена"
            );
        }

        return result.get();
    }
}