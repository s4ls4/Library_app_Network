package repository;


import domain.BaseEntity;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.Paging.Page;
import repository.Paging.PageImplementation;
import repository.Paging.Pageable;
import repository.Paging.PagingRepository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class DBRepository<ID extends Serializable, T extends BaseEntity<ID>> implements PagingRepository<ID, T> {
    private Validator<T> validator;
    private static final String URL = "jdbc:postgresql://localhost:5432/LibraryApp";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "parola";

    public DBRepository(Validator<T> validator) {
        this.validator = validator;
    }

    public abstract Optional<T> saveInDB(T entity);

    public abstract Optional<T> deleteFromDB(ID id);

    public abstract Optional<T> updateInDB(T entity);

    public abstract Optional<T> getFromDB(ID id);


    public abstract Set<T> findAllFromDB();

    public Connection connectToDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;

    }

    @Override
    public Optional<T> findOne(Optional<ID> id) {
        return this.getFromDB(id.get());
    }

    @Override
    public Optional<T> save(Optional<T> entity) throws ValidatorException {
        validator.validate(entity.get());
        return this.saveInDB(entity.get());
    }

    @Override
    public Optional<T> delete(Optional<ID> id) {
        return this.deleteFromDB(id.get());
    }

    @Override
    public Iterable<T> findAll() {
        return this.findAllFromDB();
    }

    @Override
    public Optional update(Optional<T> entity) throws ValidatorException {
        validator.validate(entity.get());
        return this.updateInDB(entity.get());
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Set<T> entities = findAllFromDB();
        AtomicInteger counter = new AtomicInteger(0);
        List<T> list = (new ArrayList<>(entities.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / pageable.getPageSize())).values()))
                .get(pageable.getPageNumber());
        return new PageImplementation<>(pageable, list.stream());
    }
}
