package repository;

import domain.Purchase;
import domain.validators.Validator;

import java.sql.*;
import java.util.*;

public class PurchaseDBRepo extends DBRepository<Long,Purchase> {
    private static final String URL = "jdbc:postgresql://localhost:5432/LibraryApp";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "98blionlion";

    public PurchaseDBRepo(Validator<Purchase> validator){
        super(validator);
    }


    public Set<Purchase> findAllFromDB(){

        Set<Purchase> purchases = new HashSet<>();
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


    public Optional<Purchase> saveInDB(Purchase purchase) {

        String sql = "insert into purchases(bid, cid, pid) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, purchase.getIdBook());
            statement.setLong(2, purchase.getIdClient());
            statement.setLong(3, purchase.getId());


            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(purchase);

    }

    @Override
    public Optional<Purchase> deleteFromDB(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Purchase> updateInDB(Purchase entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Purchase> getFromDB(Long aLong) {
        return Optional.empty();
    }
}
