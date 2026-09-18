import java.util.Scanner;

/**
 * InputValidator.java
 *
 * Dedicated class for validating user input from the console.
 * Demonstrates: Exception Handling, try-catch, Modular programming.
 */
public class InputValidator {

    private Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Reads an integer from the console. Keeps asking until a valid
     * whole number is entered. Handles NumberFormatException.
     */
    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Reads a positive double (amount) from the console.
     * Keeps asking until a valid amount greater than zero is entered.
     */
    public double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(line);
                if (value <= 0) {
                    System.out.println("Amount must be greater than 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a non-empty string from the console.
     */
    public String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("This field cannot be empty. Please try again.");
                continue;
            }
            return line;
        }
    }

    /**
     * Reads and validates a date string in dd-MM-yyyy format.
     * Performs a basic structural check rather than a full calendar
     * validation, which is appropriate for this project's scope.
     */
    public String readValidDate(String prompt) {
        while (true) {
            String date = readNonEmptyString(prompt);
            if (isValidDateFormat(date)) {
                return date;
            }
            System.out.println("Invalid date format. Please use dd-MM-yyyy (e.g. 15-09-2026).");
        }
    }

    private boolean isValidDateFormat(String date) {
        // Expected format: dd-MM-yyyy
        String[] parts = date.split("-");
        if (parts.length != 3) {
            return false;
        }
        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Reads a category selection from the console menu (1-6) and
     * converts it into an Expense.Category enum value.
     */
    public Expense.Category readCategory() {
        Expense.Category[] categories = Expense.Category.values();
        while (true) {
            System.out.println("Select Category:");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }
            System.out.print("Enter choice: ");
            String line = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= categories.length) {
                    return categories[choice - 1];
                }
                System.out.println("Invalid category selection. Please choose a number from the list.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}