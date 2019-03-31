package repository;


import domain.Book;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookFileRepository extends InMemoryRepository<Long, Book> {
    private String fileName;

    public BookFileRepository(Validator<Book> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String serialNumber = items.get(1);
                String name = items.get((2));
                String author = items.get((3));
                int price = Integer.parseInt(items.get(4));

                Book book = new Book(serialNumber, name, author, price);
                book.setId(id);

                try {
                    super.save(Optional.ofNullable(book));
                } catch (ValidatorException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Optional<Book> save(Optional<Book> entity) throws ValidatorException {
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity.get());
        return Optional.empty();
    }

    private void saveToFile(Book entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.newLine();
            bufferedWriter.write(
                    entity.getId() + "," + entity.getSerialNumber() + "," + entity.getName() + "," + entity.getAuthor() + "," + entity.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

