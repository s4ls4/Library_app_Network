package domain.validators;

import domain.Book;
import domain.Client;

import java.util.function.Predicate;

public enum ValidatorsClient {
    E1 (1, o -> o.getSerialNumber().equals("")),
    E2 (2, o -> o.getName().equals("")),
    E4 (4, o -> o.getSpent() < 0 || o.getSpent() >= 1000),
    E5 (5, o -> !o.getName().matches("[a-zA-Z. ]+")),
    E6 (6, o -> !String.valueOf(o.getId()).matches("[0-9 ]+"));

    int code;
    Predicate<Client> predicate;

    ValidatorsClient(int code, Predicate<Client> predicate) {
        this.code = code;
        this.predicate = predicate;
    }
}
