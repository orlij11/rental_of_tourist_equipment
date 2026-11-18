package ru.vsu.cs.repository;

import ru.vsu.cs.domain.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    void save(Rental rental);

    Optional<Rental> findById(long id);

    List<Rental> findAll();

    List<Rental> findByEquipmentId(long equipmentId);
}