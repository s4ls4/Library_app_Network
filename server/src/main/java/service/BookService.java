package service;

import domain.Book;
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

public class BookService {

    private PagingRepository<Long, Book> repository;
    private ExecutorService executorService;
    private int page = 0;
    private int size = 1;


    /**
     * constructor for the book service
     * @param repository - the repository
     */
    public BookService(ExecutorService executorService, PagingRepository<Long, Book> repository) {
        this.repository = repository;
        this.executorService = executorService;
    }

    public void setPageSize(int size) {
        executorService.submit(() -> {
            this.size = size;
            this.page = 0;
        });
    }

    public Future<Set<Book>> getNextBook() {
        return executorService.submit(() -> {
            Pageable pageable = PageRequest.of(size, page);
            try {
                Page<Book> bookPage = repository.findAll(pageable);
                page = bookPage.nextPageable().getPageNumber();
                return bookPage.getContent().collect(Collectors.toSet());
            } catch (IndexOutOfBoundsException ex) {
                page = 0;
                return new HashSet<>();
            }
        });
    }

    /**
     * Function that adds a book
     * @param book a book object
     * @throws ValidatorException
     */
    public void addBook(Book book) throws ValidatorException {
        executorService.submit(() -> {
            repository.save(Optional.ofNullable(book));
        });
    }


    /**
     * Function that allows access to all the entities
     * @return a stream with all the book objects
     */
    public Future<Set<Book>> getAllBooks() {
        return executorService.submit( () -> {
            Iterable<Book> books = this.repository.findAll();
            return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
        });
    }

    /**
     * Function that deletes a book
     * @param id the id of the required book
     */
    public void deleteBook(Long id) {
        executorService.submit(() -> {
            repository.delete(Optional.ofNullable(id));
        });
    }

    /**
     * Function that updates a book
     * @param book the new object
     * @throws ValidatorException
     */
    public void updateBook(Book book) throws ValidatorException {
        executorService.submit(() -> {
            repository.update(Optional.ofNullable(book));
        });
    }
}
