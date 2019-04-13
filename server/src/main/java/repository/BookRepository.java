package repository;

import domain.Book;
import domain.validators.ValidatorException;
import repository.Paging.Page;
import repository.Paging.Pageable;

import java.util.List;

public interface BookRepository {

    Book findOne(Long id);
    List<Book> findAll();
    void save(Book book) throws ValidatorException;
    void delete(Long id);
    void update(Book book) throws ValidatorException;
    Page<Book> findAll(Pageable pageable);
}
