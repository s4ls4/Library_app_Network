package repository;

/**
 * Author: Stefi Nicoara
 */

import domain.BaseEntity;
import domain.validators.ValidatorException;

import java.io.Serializable;
import java.util.Optional;

public interface Repository<ID extends Serializable, T extends BaseEntity<ID>> {
    /**
     * Find the entity with the given id.
     * @param id
     *            must be not null.
     * @return an Optional encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */

    Optional<T> findOne(Optional<ID> id);

    /**
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Saves the given entity
     * @param entity must not be null
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws ValidatorException if the entity is not valid.
     */
    Optional<T> save(Optional<T> entity) throws ValidatorException;

    /**
     * Removes the entity with the given id
     * @param id must not be null
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     */
    Optional<T> delete(Optional<ID> id);

    /**
     *  Updates a certain entity
     * @param entity the new values as object
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws ValidatorException if the entity is not valid
     */
    Optional<T> update(Optional<T> entity) throws ValidatorException;
}