import java.util.Scanner;

/**
 * Menu.java
 *
 * Displays the main console menu, reads the user's choice, and
 * delegates work to the appropriate manager classes.
 * Demonstrates: Modular programming (connects all classes together).
 */
public class Menu {

    private Scanner scanner;
    private InputValidator validator;
    private User user;
    private ExpenseManager expenseManager;
    private Budget budget;
    private FileManager fileManager;

    private static final String DATA_FILE_PATH = "data/expenses.txt";

    public Menu(User user, ExpenseManager expenseManager, Budget budget) {
        this.scanner = new Scanner(System.in);
        this.validator = new InputValidator(scanner);
        this.user = user;
        this.expenseManager = expenseManager;
        this.budget = budget;
        this.fileManager = new FileManager(DATA_FILE_PATH);
    }

    public void start() {
        System.out.println("Welcome, " + user.getName() + "!");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = validator.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    expenseManager.viewAllExpenses();
                    break;
                case 3:
                    searchExpense();
                    break;
                case 4:
                    updateExpense();
                    break;
                case 5:
                    deleteExpense();
                    break;
                case 6:
                    setBudget();
                    break;
                case 7:
                    viewBudget();
                    break;
                case 8:
                    generateReport();
                    break;
                case 9:
                    saveData();
                    break;
                case 10:
                    exitApplication();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 10.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("   STUDENT EXPENSE & BUDGET MANAGER");
        System.out.println("========================================");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Search Expense");
        System.out.println("4. Update Expense");
        System.out.println("5. Delete Expense");
        System.out.println("6. Set Monthly Budget");
        System.out.println("7. View Budget");
        System.out.println("8. Generate Report");
        System.out.println("9. Save Data");
        System.out.println("10. Exit");
        System.out.println("========================================");
    }

    // ---------- Menu option 1 ----------
    private void addExpense() {
        System.out.println("\n-- Add Expense --");
        int id = validator.readInt("Enter Expense ID: ");

        if (expenseManager.idExists(id)) {
            System.out.println("An expense with this ID already exists.");
            return;
        }

        String description = validator.readNonEmptyString("Enter Description: ");
        double amount = validator.readPositiveDouble("Enter Amount: ");
        Expense.Category category = validator.readCategory();
        String date = validator.readValidDate("Enter Date (dd-MM-yyyy): ");

        Expense expense = new Expense(id, description, amount, category, date);
        boolean added = expenseManager.addExpense(expense);

        if (added) {
            System.out.println("Expense added successfully.");
            fileManager.saveExpenses(expenseManager.getExpenses());
            checkBudgetWarning();
        } else {
            System.out.println("An expense with this ID already exists.");
        }
    }

    // ---------- Menu option 3 ----------
    private void searchExpense() {
        System.out.println("\n-- Search Expense --");
        int id = validator.readInt("Enter Expense ID to search: ");
        Expense found = expenseManager.searchExpense(id);
        if (found == null) {
            System.out.println("Expense not found.");
        } else {
            System.out.println("Expense found:");
            System.out.println(found);
        }
    }

    // ---------- Menu option 4 ----------
    private void updateExpense() {
        System.out.println("\n-- Update Expense --");
        int id = validator.readInt("Enter Expense ID to update: ");
        Expense existing = expenseManager.searchExpense(id);

        if (existing == null) {
            System.out.println("Expense not found.");
            return;
        }

        System.out.println("Current details: " + existing);
        String description = validator.readNonEmptyString("Enter new Description: ");
        double amount = validator.readPositiveDouble("Enter new Amount: ");
        Expense.Category category = validator.readCategory();
        String date = validator.readValidDate("Enter new Date (dd-MM-yyyy): ");

        boolean updated = expenseManager.updateExpense(id, description, amount, category, date);
        if (updated) {
            System.out.println("Expense updated successfully.");
            fileManager.saveExpenses(expenseManager.getExpenses());
            checkBudgetWarning();
        } else {
            System.out.println("Expense not found.");
        }
    }

    // ---------- Menu option 5 ----------
    private void deleteExpense() {
        System.out.println("\n-- Delete Expense --");
        int id = validator.readInt("Enter Expense ID to delete: ");
        boolean deleted = expenseManager.deleteExpense(id);
        if (deleted) {
            System.out.println("Expense deleted successfully.");
            fileManager.saveExpenses(expenseManager.getExpenses());
        } else {
            System.out.println("Expense not found.");
        }
    }

    // ---------- Menu option 6 ----------
    private void setBudget() {
        System.out.println("\n-- Set Monthly Budget --");
        double amount = validator.readPositiveDouble("Enter Monthly Budget: ");
        budget.setMonthlyBudget(amount);
        System.out.println("Monthly budget set successfully.");
        checkBudgetWarning();
    }

    // ---------- Menu option 7 ----------
    private void viewBudget() {
        System.out.println("\n-- Budget Status --");
        budget.displayBudgetStatus(expenseManager.getTotalSpending());
    }

    private void checkBudgetWarning() {
        double totalSpent = expenseManager.getTotalSpending();
        if (budget.getMonthlyBudget() > 0 && budget.isBudgetExceeded(totalSpent)) {
            System.out.println("WARNING: You have exceeded your monthly budget!");
        }
    }

    // ---------- Menu option 8 ----------
    private void generateReport() {
        System.out.println("\n-- Generate Report --");
        ReportGenerator reportGenerator = new ReportGenerator(expenseManager.getExpenses());
        ReportThread reportThread = new ReportThread(reportGenerator);

        // start() begins execution of run() on a new thread
        reportThread.start();

        try {
            // join() makes the main thread wait for the report thread
            // to finish before continuing, so the menu doesn't print
            // again until the report is fully generated
            reportThread.join();
        } catch (InterruptedException e) {
            System.out.println("Report generation was interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    // ---------- Menu option 9 ----------
    private void saveData() {
        fileManager.saveExpenses(expenseManager.getExpenses());
        System.out.println("Data saved successfully to " + DATA_FILE_PATH);
    }

    // ---------- Menu option 10 ----------
    private void exitApplication() {
        fileManager.saveExpenses(expenseManager.getExpenses());
        System.out.println("Data saved. Goodbye, " + user.getName() + "!");
        scanner.close();
    }
}