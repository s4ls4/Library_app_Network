package repository.Paging;

import java.util.stream.Stream;

public class PageImplementation<T> implements Page<T> {
    private Pageable pageable;
    private Stream<T> content;

    public PageImplementation(Pageable pageable, Stream<T> content){
        this.pageable = pageable;
        this.content = content;
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public Pageable nextPageable() {
        return PageRequest.of(pageable.getPageSize(), pageable.getPageNumber() + 1);
    }

    @Override
    public Stream<T> getContent() {
        return content;
    }
}
