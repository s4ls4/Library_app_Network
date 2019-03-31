package repository;

import domain.Book;
import domain.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BookDBRepo extends DBRepository<Long, Book> {

    private static final String URL = "jdbc:postgresql://localhost:5432/LibraryApp";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "98blionlion";

    public BookDBRepo(Validator<Book> validator){
        super(validator);
    }

    @Override
    public Optional<Book> saveInDB(Book book) {
        String sql = "insert into books(bname,\"bserialNumber\",author,price,bid) values (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getName());
            statement.setString(2, book.getSerialNumber());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getPrice());
            statement.setLong(5, book.getId());

            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(book);

    }

    @Override
    public Optional<Book> deleteFromDB(Long id) {
        String sql = "DELETE FROM books WHERE bid=?";
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

    @Override
    public Optional<Book> updateInDB(Book book) {
        String sql = "UPDATE book SET \"bserialNumber\"=?, bname=?, author=?, price=? where bid=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getSerialNumber());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getPrice());
            statement.setLong(5, book.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> getFromDB(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Set<Book> findAllFromDB() {
        Set<Book> books = new HashSet<>();
        String sql ="SELECT * FROM books";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("bid");
                String serialNumber = resultSet.getString("bserialNumber");
                String name = resultSet.getString("bname");
                String author = resultSet.getString("author");
                int price = resultSet.getInt("price");

                Book newBook = new Book(serialNumber, name,author, price);
                newBook.setId(id);
                books.add(newBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
