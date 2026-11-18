package ru.vsu.cs.service;

import ru.vsu.cs.domain.Equipment;
import ru.vsu.cs.exception.AppException;
import ru.vsu.cs.exception.ErrorCode;
import ru.vsu.cs.id.IdProvider;
import ru.vsu.cs.repository.EquipmentRepository;
import ru.vsu.cs.validation.InputValidator;

import java.util.List;
import java.util.Optional;

public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final IdProvider idProvider;
    private final InputValidator validator;

    public EquipmentService(EquipmentRepository equipmentRepository,
                            IdProvider idProvider,
                            InputValidator validator) {
        this.equipmentRepository = equipmentRepository;
        this.idProvider = idProvider;
        this.validator = validator;
    }

    public Equipment addEquipment(String name, String inventoryNumber) {
        validator.validateEquipment(name, inventoryNumber);

        String trimmedName = name.trim();
        String trimmedInventoryNumber = inventoryNumber.trim();

        if (equipmentRepository
                .findByInventoryNumber(trimmedInventoryNumber)
                .isPresent()) {
            throw new AppException(
                    ErrorCode.BUSINESS_RULE,
                    "Снаряжение с инвентарным номером "
                            + trimmedInventoryNumber + " уже существует"
            );
        }

        Equipment equipment = new Equipment(
                idProvider.nextId(),
                trimmedName,
                trimmedInventoryNumber
        );

        equipmentRepository.save(equipment);
        return equipment;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Equipment getEquipmentById(long id) {
        Optional<Equipment> result = equipmentRepository.findById(id);

        if (result.isEmpty()) {
            throw new AppException(
                    ErrorCode.NOT_FOUND,
                    "Снаряжение с ID " + id + " не найдено"
            );
        }

        return result.get();
    }
}