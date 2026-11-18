package ru.vsu.cs.repository.memory;

import ru.vsu.cs.domain.Client;
import ru.vsu.cs.repository.ClientRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryClientRepository implements ClientRepository {
    private final Map<Long, Client> clients = new LinkedHashMap<>();

    @Override
    public void save(Client client) {
        clients.put(client.getId(), client);
    }

    @Override
    public Optional<Client> findById(long id) {
        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clients.values());
    }
}