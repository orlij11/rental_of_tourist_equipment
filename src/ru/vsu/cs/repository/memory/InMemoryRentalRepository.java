package ru.vsu.cs.repository.memory;

import ru.vsu.cs.domain.Rental;
import ru.vsu.cs.repository.RentalRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRentalRepository implements RentalRepository {
    private final Map<Long, Rental> rentalsById = new LinkedHashMap<>();

    @Override
    public void save(Rental rental) {
        rentalsById.put(rental.getId(), rental);
    }

    @Override
    public Optional<Rental> findById(long id) {
        return Optional.ofNullable(rentalsById.get(id));
    }

    @Override
    public List<Rental> findAll() {
        return new ArrayList<>(rentalsById.values());
    }

    @Override
    public List<Rental> findByEquipmentId(long equipmentId) {
        List<Rental> result = new ArrayList<>();

        for (Rental rental : rentalsById.values()) {
            if (rental.getEquipment().getId() == equipmentId) {
                result.add(rental);
            }
        }

        return result;
    }
}