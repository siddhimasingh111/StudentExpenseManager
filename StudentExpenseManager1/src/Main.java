import java.util.ArrayList;

/**
 * Main.java
 *
 * Entry point of the Student Expense & Budget Manager application.
 * Creates the core objects, loads any previously saved data,
 * and starts the menu-driven interface.
 */
public class Main {

    private static final String DATA_FILE_PATH = "data/expenses.txt";

    public static void main(String[] args) {
        // Create the core application objects
        User user = new User("Student");
        ExpenseManager expenseManager = new ExpenseManager();
        Budget budget = new Budget();

        // Load previously saved expenses (if any) so data persists
        // between runs of the application
        FileManager fileManager = new FileManager(DATA_FILE_PATH);
        ArrayList<Expense> loadedExpenses = fileManager.loadExpenses();
        expenseManager.setExpenses(loadedExpenses);

        if (!loadedExpenses.isEmpty()) {
            System.out.println("Loaded " + loadedExpenses.size() + " saved expense(s) from file.");
        }

        // Start the menu-driven console interface
        Menu menu = new Menu(user, expenseManager, budget);
        menu.start();
    }
}