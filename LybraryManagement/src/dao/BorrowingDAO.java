package dao;

import model.Book;
import model.Borrowing;
import model.Member;
import utilitaire.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowingDAO {
    public void addBorrowing(Borrowing borrowing) throws SQLException {
        // SQL query to insert a new borrowing record into the database
        String sql = "INSERT INTO emprunts (membre_id, livre_id, date_emprunt, date_retour_prevue) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // set the parameter values for the prepared statement
            statement.setInt(1, borrowing.getMember().getId());// Set the member ID
            statement.setInt(2, borrowing.getBook().getId());// Set the book ID
            statement.setDate(3, java.sql.Date.valueOf(borrowing.getBorrowDate()));// Set the borrow date
            statement.setDate(4, java.sql.Date.valueOf(borrowing.getDueDate()));// set the due date

            //excecute the insert statement and get the generated ID for the nex borrowing
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                borrowing.setId(generatedKeys.getInt(1));// set the generated ID in the Borrowing object
            }
        }
    }


    public List<Borrowing> findAllBorrowings() throws SQLException {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT * FROM emprunts";
        try (Connection connection = DBconnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Borrowing borrowing = new Borrowing();
                borrowing.setId(resultSet.getInt("id"));
                // Retrieve member and book information using their IDs
                MemberDAO memberDAO = new MemberDAO();
                Member member = memberDAO.findMemberById(resultSet.getInt("membre_id"));
                borrowing.setMember(member);
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.findBookById(resultSet.getInt("livre_id"));
                borrowing.setBook(book);
                borrowing.setBorrowDate(resultSet.getDate("date_emprunt").toLocalDate());
                borrowing.setDueDate(resultSet.getDate("date_retour_prevue").toLocalDate());
                if (resultSet.getDate("date_retour_effective") != null) {
                    borrowing.setReturnDate(resultSet.getDate("date_retour_effective").toLocalDate());
                }
                borrowings.add(borrowing);
            }
        }
        return borrowings;
    }

    public Borrowing findBorrowingById(int id) throws SQLException {
        String sql = "SELECT * FROM emprunts WHERE id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Borrowing borrowing = new Borrowing();
                borrowing.setId(resultSet.getInt("id"));
                // Retrieve member information (assuming you have a MemberDAO)
                MemberDAO memberDAO = new MemberDAO();
                Member member = memberDAO.findMemberById(resultSet.getInt("membre_id"));
                borrowing.setMember(member);
                // Retrieve book information (assuming you have a BookDAO)
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.findBookById(resultSet.getInt("livre_id"));
                borrowing.setBook(book);
                borrowing.setBorrowDate(resultSet.getDate("date_emprunt").toLocalDate());
                borrowing.setDueDate(resultSet.getDate("date_retour_prevue").toLocalDate());
                if (resultSet.getDate("date_retour_effective") != null) {
                    borrowing.setReturnDate(resultSet.getDate("date_retour_effective").toLocalDate());
                }
                return borrowing;
            }
        }
        return null;
    }

    public List<Borrowing> findBorrowingsByMemberId(int memberId) throws SQLException {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT * FROM emprunts WHERE membre_id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Borrowing borrowing = new Borrowing();
                borrowing.setId(resultSet.getInt("id"));
                // Retrieve member and book objects using their IDs
                MemberDAO memberDAO = new MemberDAO();
                BookDAO bookDAO = new BookDAO();
                borrowing.setMember(memberDAO.findMemberById(resultSet.getInt("membre_id")));
                borrowing.setBook(bookDAO.findBookById(resultSet.getInt("livre_id")));
                borrowing.setBorrowDate(resultSet.getDate("date_emprunt").toLocalDate());
                borrowing.setDueDate(resultSet.getDate("date_retour_prevue").toLocalDate());
                borrowing.setReturnDate(resultSet.getDate("date_retour_effective") != null ?
                        resultSet.getDate("date_retour_effective").toLocalDate() : null);
                borrowings.add(borrowing);
            }
        }
        return borrowings;
    }

    public List<Borrowing> findBorrowingsByBookId(int bookId) throws SQLException {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT * FROM emprunts WHERE livre_id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Borrowing borrowing = new Borrowing();
                borrowing.setId(resultSet.getInt("id"));
                // Retrieve member and book objects using their IDs
                MemberDAO memberDAO = new MemberDAO();
                BookDAO bookDAO = new BookDAO();
                borrowing.setMember(memberDAO.findMemberById(resultSet.getInt("membre_id")));
                borrowing.setBook(bookDAO.findBookById(resultSet.getInt("livre_id")));
                borrowing.setBorrowDate(resultSet.getDate("date_emprunt").toLocalDate());
                borrowing.setDueDate(resultSet.getDate("date_retour_prevue").toLocalDate());
                borrowing.setReturnDate(resultSet.getDate("date_retour_effective") != null ?
                        resultSet.getDate("date_retour_effective").toLocalDate() : null);
                borrowings.add(borrowing);
            }
        }
        return borrowings;
    }

    public void updateBorrowing(Borrowing borrowing) throws SQLException {
        String sql = "UPDATE emprunts SET date_retour_effective = ? WHERE id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(borrowing.getReturnDate()));
            statement.setInt(2, borrowing.getId());
            statement.executeUpdate();
        }
    }

    public void deleteBorrowing(int borrowingId) throws SQLException {
        String sql = "DELETE FROM emprunts WHERE id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, borrowingId);
            statement.executeUpdate();
        }
    }
}
