package UserInterface;

import model.Book;
import model.Borrowing;
import model.Member;
import service.LibraryService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public interface ConsoleInterface {
    public class LibraryUI {
        private LibraryService libraryService;
        private Scanner scanner;

        public LibraryUI(LibraryService libraryService) {
            this.libraryService = libraryService;
            this.scanner = new Scanner(System.in);
        }

        public void start() throws SQLException {
            int choice;
            do {
                displayMenu();
                choice = getUserChoice();
                processChoice(choice);
            } while (choice != 8);
        }

        private void displayMenu() {
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Update Book");
            System.out.println("4. Search Book");
            System.out.println("5. Add Member");
            System.out.println("6. Remove Member");
            System.out.println("7. Borrow Book");
            System.out.println("8. Return Book");
            System.out.println("9. viewOverdueBorrowing");
            System.out.println("10. calculate penalties");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
        }

        private int getUserChoice() {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a number.");
                return 0;
            }
        }

        private void processChoice(int choice) throws SQLException {
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    searchBook();
                    break;
                case 5:
                    addMember();
                    break;
                case 6:
                    removeMember();
                    break;
                case 7:
                    borrowBook();
                    break;
                case 8:
                    returnBook();
                    break;
                case 9:
                    viewOverdueBorrowings();
                    break;
                case 10:
                    calculatePenalty();
                    break;
                case 11:
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        private void addBook() throws SQLException {

            scanner.nextLine(); // Consume newline

            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            System.out.print("Enter author: ");
            String author = scanner.nextLine();

            System.out.print("Enter category: ");
            String category = scanner.nextLine();

            System.out.print("Enter available copies: ");
            String availableCopiesStr = scanner.nextLine();

            int availableCopies;
            try {
                availableCopies = Integer.parseInt(availableCopiesStr); // Parse the input string to an integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for available copies. Please enter a number.");
                return; // Exit the method if the input is invalid
            }

            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setCategory(category);
            book.setAvailableCopies(availableCopies);

            libraryService.addBook(book);
            System.out.println("Book added successfully.");
        }

        private void removeBook() {
            System.out.print("Enter book ID to remove: ");
            int bookId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                libraryService.deleteBook(bookId);
                System.out.println("Book removed successfully.");
            } catch (Exception e) {
                System.out.println("Error removing book: " + e.getMessage());
            }
        }

        private void updateBook() throws SQLException {
            System.out.print("Enter book ID to update: ");
            int bookId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Book book = libraryService.findBookById(bookId);
            if (book != null) {
                System.out.print("Enter new title (or press Enter to keep current): ");
                String newTitle = scanner.nextLine();
                if (!newTitle.isEmpty()) {
                    book.setTitle(newTitle);
                }

                System.out.print("Enter new author (or press Enter to keep current): ");
                String newAuthor = scanner.nextLine();
                if (!newAuthor.isEmpty()) {
                    book.setAuthor(newAuthor);
                }

                System.out.print("Enter new category (or press Enter to keep current): ");
                String newCategory = scanner.nextLine();
                if (!newCategory.isEmpty()) {
                    book.setCategory(newCategory);
                }

                System.out.print("Enter new available copies (or press Enter to keep current): ");
                String availableCopiesStr = scanner.nextLine();
                if (!availableCopiesStr.isEmpty()) {
                    try {
                        book.setAvailableCopies(Integer.parseInt(availableCopiesStr));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for available copies.");
                    }
                }

                libraryService.updateBook(book);
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Book not found.");
            }
        }

        private void searchBook() throws SQLException {

            scanner.nextLine();//consume newline

            System.out.print("Enter search keyword: ");
            String keyword = scanner.nextLine();
            List<Book> foundBooks = libraryService.findBooksByTitle(keyword);

            if (foundBooks.isEmpty()) {
                System.out.println("No books found.");
            } else {
                System.out.println("Found books:");
                for (Book book : foundBooks) {
                    System.out.println(book);// to call implicitly the ToString() method;
                }
            }
        }

        private void addMember() throws SQLException {

            scanner.nextLine();

            System.out.print("Enter member name: ");
            String name = scanner.nextLine();
            System.out.print("Enter member email: ");
            String email = scanner.nextLine();

            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setEnrollmentDate(LocalDate.now());

            libraryService.addMember(member);
            System.out.println("Member added successfully.");
        }

        private void removeMember() {
            System.out.print("Enter member ID to remove: ");
            int memberId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                libraryService.deleteMember(memberId);
                System.out.println("Member removed successfully.");
            } catch (Exception e) {
                System.out.println("Error removing member: " + e.getMessage());
            }
        }

        private void borrowBook() throws SQLException {
            System.out.print("Enter member ID: ");
            int memberId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter book ID: ");
            int bookId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Member member = libraryService.findMemberById(memberId);
            Book book = libraryService.findBookById(bookId);

            if (member != null && book != null) {
                System.out.print("Enter dueDate (YYYY-MM-DD): ");
                String dueDateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dueDate;
                try{
                    dueDate = LocalDate.parse(dueDateString, formatter);
                } catch (DateTimeParseException e){
                    System.out.println("Invalid date format. please enter in the format YYYY-MM-DD. ");
                    return;
                }
                libraryService.borrowBook(member, book, dueDate);
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Member or book not found.");
            }
        }

        private void returnBook() {
            System.out.print("Enter borrowing ID: ");
            int borrowingId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                Borrowing borrowing = libraryService.findBorrowingById(borrowingId);
                if (borrowing != null) {
                    libraryService.returnBook(borrowing);
                    System.out.println("Book returned successfully.");
                } else {
                    System.out.println("Borrowing not found.");
                }
            } catch (Exception e) {
                System.out.println("Error returning book: " + e.getMessage());
            }
        }

        private void viewOverdueBorrowings() throws SQLException {
            List<Borrowing> overdueBorrowings = libraryService.findOverdueBorrowings();
            if (overdueBorrowings.isEmpty()) {
                System.out.println("No overdue borrowings.");
            } else {
                System.out.println("Overdue Borrowings:");
                for (Borrowing borrowing : overdueBorrowings) {
                    System.out.println("Borrowing ID: " + borrowing.getId() +
                            ", Member: " + borrowing.getMember().getName() +
                            ", Book: " + borrowing
                    );
                }
            }
        }

        private void calculatePenalty() throws SQLException{
            System.out.print("Enter borrowing ID: ");
            int borrowingId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Borrowing borrowing = libraryService.findBorrowingById(borrowingId);
            if (borrowing != null) {
                double penalty = libraryService.calculatePenalty(borrowing);
                if (penalty > 0) {
                    System.out.println("Penalty for borrowing ID " + borrowingId + ": " + penalty + " F CFA");
                } else {
                    System.out.println("No penalty for this borrowing.");
                }
            } else {
                System.out.println("Borrowing not found.");
            }
        }

    }
}



