package model;
import java.time.LocalDate;


public class Borrowing {
    private int id;
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    // constructors
    public Borrowing (){

    }

    public Borrowing (int id, Member member, Book book, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate){
        this.id=id;
        this.member=member;
        this.book=book;
        this.borrowDate=borrowDate;
        this.dueDate=dueDate;
        this.returnDate=returnDate;

    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
