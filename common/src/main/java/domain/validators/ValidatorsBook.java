package domain.validators;

import domain.Book;

import java.util.function.Predicate;

public enum ValidatorsBook {
    E1 (1, o -> o.getSerialNumber().equals("")),
    E2 (2, o -> o.getName().equals("")),
    E3 (3, o -> o.getAuthor().equals("")),
    E4 (4, o -> o.getPrice() <= 0 || o.getPrice() >= 1000),
    E5 (5, o -> !o.getAuthor().matches("[a-zA-Z. ]+")),
    E6 (6, o -> !String.valueOf(o.getId()).matches("[0-9 ]+"));

    int code;
    Predicate<Book> predicate;

    ValidatorsBook(int code, Predicate<Book> predicate) {
        this.code = code;
        this.predicate = predicate;
    }
}
