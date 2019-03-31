package repository;

import domain.Purchase;
import domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PurchaseDBRepo extends InMemoryRepository<Long, Purchase> {
    private static final String URL = "jdbc:postgresql://localhost:5432/LibraryApp";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "98blionlion";

    public PurchaseDBRepo(Validator<Purchase> validator){
        super(validator);
    }


    public Iterable<Purchase> findAll(){

        List<Purchase> purchases = new ArrayList<>();
        String sql ="SELECT * FROM purchases";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long bid = resultSet.getLong("bid");
                Long cid = resultSet.getLong("cid");

                Purchase newPurchase = new Purchase(bid,cid);
                purchases.add(newPurchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchases;
    }


    public Optional<Purchase> save(Optional<Purchase> purchase) {

        String sql = "insert into purchases(bid, cid, pid) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, purchase.get().getIdBook());
            statement.setLong(2, purchase.get().getIdClient());
            statement.setLong(3, purchase.get().getId());


            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(purchase.get());

    }
}
