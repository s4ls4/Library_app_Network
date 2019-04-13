package repository;

import domain.Book;
import domain.Client;
import domain.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import repository.Paging.Page;
import repository.Paging.PageImplementation;
import repository.Paging.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BookRepositoryImpl implements BookRepository{
    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private Validator<Book> clientValidator;


    @Override
    public Book findOne(Long id) {
        String sql = "SELECT * FROM Books WHERE id='" + id +"';";
        System.out.println(sql);
        return jdbcOperations.query(sql, (rs, rowNum) -> {
            Long newId = rs.getLong("id");
            String name = rs.getString("name");
            Book b = new Book(b);
            b.setId(id);
            return b;
        }).get(0);
    }

    @Override
    public List<Book> findAll() {
        String sql = "select * from Books";
        return jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            Book b = new Book(b);
            b.setId(id);
            return b;
        });
    }

    @Override
    public void save(Book book) {
        String sql = "INSERT INTO Books VALUES(?,?)";
        clientValidator.validate(book);
        jdbcOperations.update(sql, book.getId(), book.getName());
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE Books SET name = ? WHERE id = ?";
        clientValidator.validate(book);
        jdbcOperations.update(sql, book.getName(), book.getId());
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        List<Book> entities = findAll();
        AtomicInteger counter = new AtomicInteger(0);
        List<Book> l = (new ArrayList<>(entities.stream()//.sorted(Comparator.comparing(it -> it.getId()))
                .collect(Collectors.groupingBy(it -> (counter.getAndIncrement()) / pageable.getPageSize()))
                .values()).get(pageable.getPageNumber()));

        return new PageImplementation<>(pageable, l.stream());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Books WHERE ID=?";
        jdbcOperations.update(sql, id);
    }
}
