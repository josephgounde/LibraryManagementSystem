import UserInterface.ConsoleInterface;
import service.LibraryService;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        // Create an instance of LibraryService
        LibraryService libraryService = new LibraryService();

        // Create an instance of LibraryUI and pass the LibraryService object
        ConsoleInterface.LibraryUI libraryUI = new ConsoleInterface.LibraryUI(libraryService);

        // Start the library UI
        libraryUI.start();
    }
}