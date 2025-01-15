package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import utilitaire.DBconnection;

public class BookDAO {
    public void addBook(Book book) throws SQLException {
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO livres (titre, auteur, categorie, nombre_exemplaires) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setInt(4, book.getAvailableCopies());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getInt(1));
            }
        }
    }

    public void updateBook(Book book) throws SQLException {
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE livres SET titre = ?, auteur = ?, categorie = ?, nombre_exemplaires = ? WHERE id = ?")) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setInt(4, book.getAvailableCopies());
            statement.setInt(5, book.getId());
            statement.executeUpdate();
        }
    }

    public void deleteBook(int bookId) throws SQLException {
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM livres WHERE id = ?")) {
            statement.setInt(1, bookId);
            statement.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DBconnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM livres")) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("titre"));
                book.setAuthor(resultSet.getString("auteur"));
                book.setCategory(resultSet.getString("categorie"));
                book.setAvailableCopies(resultSet.getInt("nombre_exemplaires"));
                books.add(book);
            }
        }
        return books;
    }

    public Book findBookById(int id) throws SQLException {
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM livres WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("titre"));
                book.setAuthor(resultSet.getString("auteur"));
                book.setCategory(resultSet.getString("categorie"));
                book.setAvailableCopies(resultSet.getInt("nombre_exemplaires"));
                return book;
            }
        }
        return null;
    }

    public List<Book> findBooksByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM livres WHERE titre LIKE ?")) {
            statement.setString(1, "%" + title + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("titre"));
                book.setAuthor(resultSet.getString("auteur"));
                book.setCategory(resultSet.getString("categorie"));
                book.setAvailableCopies(resultSet.getInt("nombre_exemplaires"));
                books.add(book);
            }
        }
        return books;
    }
}
