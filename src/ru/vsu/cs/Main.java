package ru.vsu.cs;

import ru.vsu.cs.id.IdProvider;
import ru.vsu.cs.repository.memory.InMemoryClientRepository;
import ru.vsu.cs.repository.memory.InMemoryEquipmentRepository;
import ru.vsu.cs.repository.memory.InMemoryRentalRepository;
import ru.vsu.cs.service.ClientService;
import ru.vsu.cs.service.EquipmentService;
import ru.vsu.cs.service.RentalService;
import ru.vsu.cs.ui.ConsoleHelper;
import ru.vsu.cs.ui.ConsoleUI;
import ru.vsu.cs.validation.InputValidator;

public class Main {
    public static void main(String[] args) {
        InputValidator validator = new InputValidator();

        ClientService clientService = new ClientService(
                new InMemoryClientRepository(),
                new IdProvider(),
                validator
        );

        EquipmentService equipmentService = new EquipmentService(
                new InMemoryEquipmentRepository(),
                new IdProvider(),
                validator
        );

        RentalService rentalService = new RentalService(
                new InMemoryRentalRepository(),
                clientService,
                equipmentService,
                new IdProvider(),
                validator
        );

        ConsoleUI ui = new ConsoleUI(
                new ConsoleHelper(),
                clientService,
                equipmentService,
                rentalService
        );

        ui.run();
    }
}