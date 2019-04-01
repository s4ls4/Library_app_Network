package repository;

import domain.Client;
import domain.validators.Validator;

import java.sql.*;
import java.util.*;

public class ClientDBRepo extends DBRepository<Long,Client> {

    private static final String URL = "jdbc:postgresql://localhost:5432/LibraryApp";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "98blionlion";

    public ClientDBRepo(Validator<Client> validator){
        super(validator);
    }


    public Set<Client> findAllFromDB(){

        Set<Client> clients = new HashSet<>();
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


    public Optional<Client> saveInDB(Client client) {
        String sql = "INSERT INTO clients(\"cserialNumber\",cname,spent,cid) values (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getSerialNumber());
            statement.setString(2, client.getName());
            statement.setInt(3, client.getSpent());
            statement.setLong(4, client.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Client> deleteFromDB(Long id) {
        String sql = "DELETE FROM clients WHERE cid=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Client> updateInDB(Client client) {
        String sql = "UPDATE client SET \"cserialNumber\"=?, cname=?, spent=? where cid=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getSerialNumber());
            statement.setString(2, client.getName());
            statement.setInt(3, client.getSpent());
            statement.setLong(4, client.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> getFromDB(Long aLong) {
        return Optional.empty();
    }


}
