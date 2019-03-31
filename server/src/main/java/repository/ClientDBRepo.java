package repository;

import domain.Client;
import domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDBRepo extends InMemoryRepository<Long, Client> {

    private static final String URL = "jdbc:postgresql://localhost:5432/LibraryApp";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "98blionlion";

    public ClientDBRepo(Validator<Client> validator){
        super(validator);
    }


    public Iterable<Client> findAll(){

        List<Client> clients = new ArrayList<>();
        String sql ="SELECT * FROM clients";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("cid");
                String serialNumber = resultSet.getString("cserialNumber");
                String name = resultSet.getString("cname");
                int spent = resultSet.getInt("spent");

                Client newClient = new Client(serialNumber, name, spent);
                newClient.setId(id);
                clients.add(newClient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }


    public Optional<Client> save(Optional<Client> client) {
        String sql = "INSERT INTO clients(\"cserialNumber\",cname,spent,cid) values (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.get().getSerialNumber());
            statement.setString(2, client.get().getName());
            statement.setInt(3, client.get().getSpent());
            statement.setLong(4, client.get().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Client> delete(Optional<Long> id) {
        String sql = "DELETE FROM clients WHERE cid=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id.get());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Client> update(Optional<Client> client) {
        String sql = "UPDATE client SET \"cserialNumber\"=?, cname=?, spent=? where cid=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.get().getSerialNumber());
            statement.setString(2, client.get().getName());
            statement.setInt(3, client.get().getSpent());
            statement.setLong(4, client.get().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
