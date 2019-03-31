package domain.validators;

import domain.Book;

import java.util.Arrays;

public class BookValidator implements Validator<Book> {

    @Override
    public void validate(Book book) throws ValidatorException
    {

        Arrays.stream(ValidatorsBook.values()).filter(v -> v.predicate.test(book)).
                findFirst().ifPresent(s->{throw new ValidatorException("Invalid input!");});

    }
}
