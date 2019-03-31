package repository;

import domain.Client;
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

public class ClientFileRepository extends InMemoryRepository<Long, Client> {
    private String fileName;

    public ClientFileRepository(Validator<Client> validator, String fileName) {
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
                int spent = Integer.parseInt(items.get(3));

                Client client = new Client(serialNumber, name, spent);
                client.setId(id);

                try {
                    super.save(Optional.ofNullable(client));
                } catch (ValidatorException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Optional<Client> save(Optional<Client> entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity.get());
        return Optional.empty();
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.newLine();
            bufferedWriter.write(
                    entity.getId() + "," + entity.getSerialNumber() + "," + entity.getName() + "," + entity.getSpent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

