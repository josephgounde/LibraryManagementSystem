package service;

import dao.BookDAO;
import dao.MemberDAO;
import dao.BorrowingDAO;
import model.Book;
import model.Member;
import model.Borrowing;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryService {
    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private BorrowingDAO borrowingDAO;

    public LibraryService() {
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        borrowingDAO = new BorrowingDAO();
    }

    // book methods
    public void addBook(Book book) throws SQLException {
        bookDAO.addBook(book);
    }

    public void updateBook(Book book) {
        try {
            bookDAO.updateBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(int bookId) {
        try {
            bookDAO.deleteBook(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> findBooksByTitle(String title) throws SQLException {
        return bookDAO.findBooksByTitle(title);
    }

    public Book findBookById(int id) throws SQLException {
        return bookDAO.findBookById(id);
    }

    public List<Book> findAllBooks() {
        try {
            return bookDAO.getAllBooks();
        } catch (SQLException e) {
            // Handle the exception (e.g., log the error)
            e.printStackTrace();
            return null;
        }
    }


    // Member methods
    public void addMember(Member member) throws SQLException {
        memberDAO.addMember(member);
    }

    public Member findMemberById(int id) throws SQLException {
        return memberDAO.findMemberById(id);
    }

    public List<Member> findAllMembers() {
        try {
            return memberDAO.findAllMembers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateMember(Member member) {
        try {
            memberDAO.updateMember(member);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMember(int memberId) {
        try {
            memberDAO.deleteMember(memberId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Borrowing methods
    public void borrowBook(Member member, Book book, LocalDate dueDate) {
        Scanner scanner = null;
        try {
            if (book.getAvailableCopies() > 0) {
                Borrowing borrowing = new Borrowing();
                borrowing.setMember(member);
                borrowing.setBook(book);
                borrowing.setBorrowDate(LocalDate.now());
                borrowing.setDueDate(dueDate);
                // Calculate due date (e.g., 14 days from borrow date)
                //borrowing.setDueDate(LocalDate.now().plusDays(14));
                borrowingDAO.addBorrowing(borrowing);
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookDAO.updateBook(book);
            } else {
                System.out.println("Book is not available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Borrowing findBorrowingById(int borrowingId) {
        try {
            return borrowingDAO.findBorrowingById(borrowingId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrowing> findBorrowingsByMemberId(int memberId) {
        try {
            return borrowingDAO.findBorrowingsByMemberId(memberId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrowing> findBorrowingsByBookId(int bookId) {
        try {
            return borrowingDAO.findBorrowingsByBookId(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void returnBook(Borrowing borrowing) {
        try {
            borrowing.setReturnDate(LocalDate.now());
            borrowingDAO.updateBorrowing(borrowing);
            Book book = borrowing.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookDAO.updateBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calculatePenalty(Borrowing borrowing) {
        if (borrowing.getReturnDate() == null || borrowing.getDueDate().isAfter(borrowing.getReturnDate())) {
            return 0; // No penalty if not returned or returned on time
        }

        long daysOverdue = Period.between(borrowing.getDueDate(), borrowing.getReturnDate()).getDays();
        // Assuming a penalty rate of 100 F CFA per day (you can adjust this)
        return daysOverdue * 100;
    }

    public List<Borrowing> findOverdueBorrowings() throws SQLException {
        List<Borrowing> borrowings = borrowingDAO.findAllBorrowings(); // Retrieve all borrowings
        List<Borrowing> overdueBorrowings = new ArrayList<>();

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getDueDate().isBefore(LocalDate.now()) && borrowing.getReturnDate() == null) {
                overdueBorrowings.add(borrowing);
            }
        }

        return overdueBorrowings;
    }

}
