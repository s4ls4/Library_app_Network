package service;

import domain.Book;

import java.util.Arrays;
import java.util.List;

public class StudentServiceImpl implements IBookService{

    @Override
    public List<Book> findAll() {
        return Arrays.asList(
                new  Book("1", "asda", "adsa", 3),
                new Book("2", "asda", "adsa", 3)
        );
    }
}
