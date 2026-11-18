package ru.vsu.cs.repository.memory;

import ru.vsu.cs.domain.Equipment;
import ru.vsu.cs.repository.EquipmentRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryEquipmentRepository implements EquipmentRepository {
    private final Map<Long, Equipment> equipmentById = new LinkedHashMap<>();

    @Override
    public void save(Equipment equipment) {
        equipmentById.put(equipment.getId(), equipment);
    }

    @Override
    public Optional<Equipment> findById(long id) {
        return Optional.ofNullable(equipmentById.get(id));
    }

    @Override
    public Optional<Equipment> findByInventoryNumber(String inventoryNumber) {
        for (Equipment item : equipmentById.values()) {
            if (item.getInventoryNumber().equals(inventoryNumber)) {
                return Optional.of(item);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Equipment> findAll() {
        return new ArrayList<>(equipmentById.values());
    }
}