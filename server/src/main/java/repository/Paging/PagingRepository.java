package repository.Paging;

import domain.BaseEntity;
import repository.Repository;

import java.io.Serializable;

public interface PagingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends Repository<ID, T> {
    Page<T> findAll(Pageable pageable);

}
