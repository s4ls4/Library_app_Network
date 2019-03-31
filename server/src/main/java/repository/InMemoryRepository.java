package repository;

/**
 * Author: Stefi Nicoara
 */

import domain.BaseEntity;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.Paging.Page;
import repository.Paging.PageImplementation;
import repository.Paging.Pageable;
import repository.Paging.PagingRepository;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryRepository<ID extends Serializable, T extends BaseEntity<ID>> implements PagingRepository<ID, T> {

    private Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(Optional<ID> id) {
        if (!id.isPresent()) {
            throw new IllegalArgumentException("ID must not be null!");
        }
            return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        Set<T> allEntities = entities.entrySet()
                .stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toSet());
        return allEntities;
    }


    @Override
    public Page<T> findAll(Pageable pageable) {
        Set<T> entitiess = entities.entrySet()
                .stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toSet());
        AtomicInteger counter = new AtomicInteger(0);

        List<T> list = (new ArrayList<>(entitiess.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / pageable.getPageSize())).values()))
                .get(pageable.getPageNumber());
        return new PageImplementation<>(pageable, list.stream());
    }


    @Override
    public Optional<T> save(Optional<T> entity) throws ValidatorException {
        if (!entity.isPresent()) {
            throw new IllegalArgumentException("ID must not be null!");
        }
        validator.validate(entity.get());
        return Optional.ofNullable(entities.putIfAbsent(entity.get().getId(),
                entity.get()));
    }

    @Override
    public Optional<T> delete(Optional<ID> id) {
        if (!id.isPresent()) {
            throw new IllegalArgumentException("ID must not be null!");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(Optional<T> entity) throws ValidatorException {
        if (!entity.isPresent()) {
            throw new IllegalArgumentException("ID must not be null!");
        }
        validator.validate(entity.get());
        return Optional.ofNullable(entities.computeIfPresent(entity.get().getId(),
                (k, v) -> entity.get()));
}}
