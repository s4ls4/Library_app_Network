package service;

import domain.Purchase;
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

public class PurchaseService {

    private PagingRepository<Long, Purchase> repository;
    private ExecutorService executorService;
    private int page = 0;
    private int size = 1;

    public PurchaseService(ExecutorService executorService, PagingRepository<Long, Purchase> repository) {
        this.repository = repository;
        this.executorService = executorService;
    }

    public void setPageSize(int size) {
        this.size = size;
        this.page = 0;
    }

    public Future<Set<Purchase>> getNextPurchase() {
        return executorService.submit(() -> {
            Pageable pageable = PageRequest.of(size, page);
            try {
                Page<Purchase> purchasePage = repository.findAll(pageable);
                page = purchasePage.nextPageable().getPageNumber();
                return purchasePage.getContent().collect(Collectors.toSet());
            } catch (IndexOutOfBoundsException ex) {
                page = 0;
                return new HashSet<>();
            }
        });
    }

    public void addPurchase(Purchase purchase) throws ValidatorException {
        executorService.submit(() -> {
            repository.save(Optional.ofNullable(purchase));
        });
    }

    public Future<Set<Purchase>> getAllPurchases() {
        return executorService.submit( () -> {
            Iterable<Purchase> books = this.repository.findAll();
            return StreamSupport.stream(books.spliterator(), false)
                    .collect(Collectors.toSet());
        });
    }

}
