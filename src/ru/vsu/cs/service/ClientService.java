package ru.vsu.cs.service;

import ru.vsu.cs.domain.Client;
import ru.vsu.cs.exception.AppException;
import ru.vsu.cs.exception.ErrorCode;
import ru.vsu.cs.id.IdProvider;
import ru.vsu.cs.repository.ClientRepository;
import ru.vsu.cs.validation.InputValidator;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepository;
    private final IdProvider idProvider;
    private final InputValidator validator;

    public ClientService(ClientRepository clientRepository,
                         IdProvider idProvider,
                         InputValidator validator) {
        this.clientRepository = clientRepository;
        this.idProvider = idProvider;
        this.validator = validator;
    }

    public Client addClient(String fullName, String phone) {
        validator.validateClient(fullName, phone);

        Client client = new Client(
                idProvider.nextId(),
                fullName.trim(),
                phone.trim()
        );

        clientRepository.save(client);
        return client;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(long id) {
        Optional<Client> result = clientRepository.findById(id);

        if (result.isEmpty()) {
            throw new AppException(
                    ErrorCode.NOT_FOUND,
                    "Клиент с ID " + id + " не найден"
            );
        }

        return result.get();
    }
}