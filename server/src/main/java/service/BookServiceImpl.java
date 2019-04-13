package service;

import domain.Book;
import domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.BookRepository;
import repository.Paging.Page;
import repository.Paging.PageRequest;
import repository.Paging.Pageable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookServiceImpl implements IBookService{
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private BookRepository bookRepository;
    private int size = 1;
    private int page = 0;

    public BookServiceImpl() {
    }

    @Override
    public Set<Book> getAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.update(book);
    }

    @Override
    public Set<Book> getNextBook() {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Book> bookPage = bookRepository.findAll(pageable);
            page = bookPage.nextPageable().getPageNumber();
            return bookPage.getContent().collect(Collectors.toSet());

        } catch (IndexOutOfBoundsException e) {
            page = 0;
            return new HashSet<>();
        }
    }

    @Override
    public void setPageSize(int size) {
        this.page = 0;
        this.size = size;
    }

}
