package service;

import domain.Client;
import domain.validators.ValidatorException;
import repository.Paging.Page;
import repository.Paging.PageRequest;
import repository.Paging.Pageable;
import repository.Paging.PagingRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {

    private PagingRepository<Long, Client> repository;
    private ExecutorService executorService;
    private int page = 0;
    private int size = 1;

    public ClientService(ExecutorService executorService, PagingRepository<Long, Client> repository) {
        this.repository = repository;
        this.executorService = executorService;
    }

    public void setPageSize(int size) {
        executorService.submit(() -> {
            this.size = size;
            this.page = 0;
        });
    }

    public Future<Set<Client>> getNextClient() {
        return executorService.submit(() -> {
            Pageable pageable = PageRequest.of(size, page);
            try {
                Page<Client> clientPage = repository.findAll(pageable);
                page = clientPage.nextPageable().getPageNumber();
                return clientPage.getContent().collect(Collectors.toSet());
            } catch (IndexOutOfBoundsException ex) {
                page = 0;
                return new HashSet<>();
            }
        });
    }

    public void addClient(Client client) throws ValidatorException {
        executorService.submit(() -> {
            repository.save(Optional.ofNullable(client));
        });
    }

    public Future<Set<Client>> getAllClients() {
        return executorService.submit( () -> {
            Iterable<Client> books = this.repository.findAll();
            return StreamSupport.stream(books.spliterator(), false)
                    .collect(Collectors.toSet());
        });
    }

    public void deleteClient(Long id) {
        executorService.submit(() -> {
            repository.delete(Optional.ofNullable(id));
        });
    }

    public void updateClient(Client client) throws ValidatorException {
        executorService.submit(() -> {
            repository.update(Optional.ofNullable(client));
        });
    }

}
