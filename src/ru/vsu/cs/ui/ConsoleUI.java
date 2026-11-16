package ru.vsu.cs.ui;

import ru.vsu.cs.domain.Client;
import ru.vsu.cs.domain.Equipment;
import ru.vsu.cs.domain.Rental;
import ru.vsu.cs.domain.RentalStatus;
import ru.vsu.cs.exception.AppException;
import ru.vsu.cs.service.ClientService;
import ru.vsu.cs.service.EquipmentService;
import ru.vsu.cs.service.RentalService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class ConsoleUI {
    private final ConsoleHelper console;
    private final ClientService clientService;
    private final EquipmentService equipmentService;
    private final RentalService rentalService;

    public ConsoleUI(ConsoleHelper console,
                     ClientService clientService,
                     EquipmentService equipmentService,
                     RentalService rentalService) {
        this.console = console;
        this.clientService = clientService;
        this.equipmentService = equipmentService;
        this.rentalService = rentalService;
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();

            try {
                String choice = console.readString("Выберите пункт: ");

                switch (choice) {
                    case "1" -> addClient();
                    case "2" -> showClients();
                    case "3" -> addEquipment();
                    case "4" -> showEquipment();
                    case "5" -> createRental();
                    case "6" -> returnEquipment();
                    case "7" -> showRentals();
                    case "0" -> running = false;
                    default -> console.printLine("Нет такого пункта меню.");
                }
            } catch (AppException e) {
                console.printLine("Ошибка: " + e.getMessage());
            } catch (NoSuchElementException e) {
                running = false;
            }
        }

        console.printLine("Работа завершена.");
    }

    private void printMenu() {
        console.printLine("");
        console.printLine("Учёт туристического снаряжения");
        console.printLine("1. Добавить клиента");
        console.printLine("2. Показать клиентов");
        console.printLine("3. Добавить снаряжение");
        console.printLine("4. Показать снаряжение");
        console.printLine("5. Выдать снаряжение");
        console.printLine("6. Вернуть снаряжение");
        console.printLine("7. Показать выдачи");
        console.printLine("0. Выход");
    }

    private void addClient() {
        String fullName = console.readString("ФИО клиента: ");
        String phone = console.readString("Телефон: ");

        Client client = clientService.addClient(fullName, phone);
        console.printLine("Клиент добавлен. ID: " + client.getId());
    }

    private void showClients() {
        List<Client> clients = clientService.getAllClients();

        if (clients.isEmpty()) {
            console.printLine("Клиентов пока нет.");
            return;
        }

        console.printLine("ID | ФИО | Телефон");

        for (Client client : clients) {
            console.printLine(
                    client.getId() + " | " + client.getFullName()
                            + " | " + client.getPhone()
            );
        }
    }

    private void addEquipment() {
        String name = console.readString("Название снаряжения: ");
        String inventoryNumber = console.readString("Инвентарный номер: ");

        Equipment equipment = equipmentService.addEquipment(name, inventoryNumber);
        console.printLine("Снаряжение добавлено. ID: " + equipment.getId());
    }

    private void showEquipment() {
        List<Equipment> items = equipmentService.getAllEquipment();

        if (items.isEmpty()) {
            console.printLine("Снаряжения пока нет.");
            return;
        }

        console.printLine("ID | Название | Инвентарный номер");

        for (Equipment equipment : items) {
            console.printLine(
                    equipment.getId() + " | " + equipment.getName()
                            + " | " + equipment.getInventoryNumber()
            );
        }
    }

    private void createRental() {
        long clientId = console.readLong("ID клиента: ");
        long equipmentId = console.readLong("ID снаряжения: ");
        LocalDate issueDate = console.readDate("Дата выдачи (ГГГГ-ММ-ДД): ");
        LocalDate plannedReturnDate = console.readDate(
                "Плановая дата возврата (ГГГГ-ММ-ДД): "
        );

        Rental rental = rentalService.createRental(
                clientId, equipmentId, issueDate, plannedReturnDate
        );

        console.printLine("Выдача оформлена. ID: " + rental.getId());
    }

    private void returnEquipment() {
        long rentalId = console.readLong("ID выдачи: ");
        LocalDate actualReturnDate = console.readDate(
                "Фактическая дата возврата (ГГГГ-ММ-ДД): "
        );

        rentalService.returnEquipment(rentalId, actualReturnDate);
        console.printLine("Возврат оформлен.");
    }

    private void showRentals() {
        List<Rental> rentals = rentalService.getAllRentals();

        if (rentals.isEmpty()) {
            console.printLine("Выдач пока нет.");
            return;
        }

        for (Rental rental : rentals) {
            String status = rental.getStatus() == RentalStatus.ACTIVE
                    ? "Выдано" : "Возвращено";

            String actualReturn = rental.getActualReturnDate() == null
                    ? "не возвращено" : rental.getActualReturnDate().toString();

            console.printLine("Выдача №" + rental.getId() + " | " + status);
            console.printLine("Клиент: " + rental.getClient().getFullName());

            console.printLine(
                    "Снаряжение: " + rental.getEquipment().getName()
                            + " | " + rental.getEquipment().getInventoryNumber()
            );

            console.printLine(
                    "Выдано: " + rental.getIssueDate()
                            + " | Плановый возврат: " + rental.getPlannedReturnDate()
            );

            console.printLine("Фактический возврат: " + actualReturn);
            console.printLine("");
        }
    }
}