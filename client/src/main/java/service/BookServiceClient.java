package service;

import domain.Book;

import java.util.List;

public class BookServiceClient implements IBookService {
    @Autowired
    private IBookService bookService;

    @Override
    public List<Book> findAll() {
        return bookService.findAll();
    }
}
