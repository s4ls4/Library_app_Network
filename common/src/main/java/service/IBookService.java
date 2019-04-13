package service;

import domain.Book;
import domain.validators.ValidatorException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public interface IBookService {

    String SET_PAGE_SIZE = "setPageSize";
    void setPageSize(int size);

    String GET_NEXT_BOOK = "getNextBook";
    Set<Book> getNextBook();

    String ADD_BOOK = "addBook";
    void addBook(Book book) throws ValidatorException;

    String DELETE_BOOK = "deleteBook";
    void deleteBook(Long id) throws ValidatorException;

    String GET_ALL_BOOKS = "getAllBooks";
    Set<Book> getAllBooks();

    String UPDATE_BOOK = "updateBook";
    void updateBook(Book book) throws ValidatorException;
}
