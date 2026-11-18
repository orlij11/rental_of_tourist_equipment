package ru.vsu.cs.repository;

import ru.vsu.cs.domain.Equipment;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository {
    void save(Equipment equipment);

    Optional<Equipment> findById(long id);

    Optional<Equipment> findByInventoryNumber(String inventoryNumber);

    List<Equipment> findAll();
}