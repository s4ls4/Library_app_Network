package service;

import domain.Book;
import domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class BookServiceClient implements IBookService {
    @Autowired
    private IBookService bookService;

    public BookServiceClient() {
    }


    @Override
    public Set<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Override
    public void addBook(Book book) {
        bookService.addBook(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

    @Override
    public void updateBook(Book book) {
        bookService.updateBook(book);
    }


    @Override
    public Set<Book> getNextBook() {
        return bookService.getNextBook();
    }

    @Override
    public void setPageSize(int size) {
        bookService.setPageSize(size);
    }
}