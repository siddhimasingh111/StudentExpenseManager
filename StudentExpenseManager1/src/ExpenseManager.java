import java.util.ArrayList;

/**
 * ExpenseManager.java
 *
 * Manages the in-memory collection of expenses and provides
 * CRUD (Create, Read, Update, Delete) operations.
 * Demonstrates: ArrayList/Collections, CRUD operations, Modular programming.
 */
public class ExpenseManager {

    private ArrayList<Expense> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    // Used by FileManager to bulk-load expenses at startup
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    // Checks whether an expense with the given ID already exists
    public boolean idExists(int id) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // CREATE
    public boolean addExpense(Expense expense) {
        if (idExists(expense.getId())) {
            return false; // duplicate ID not allowed
        }
        expenses.add(expense);
        return true;
    }

    // READ (all)
    public void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.println("----------------------------------------------------------------");
        for (Expense e : expenses) {
            System.out.println(e);
        }
        System.out.println("----------------------------------------------------------------");
    }

    // READ (search by ID)
    public Expense searchExpense(int id) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null; // not found
    }

    // UPDATE
    public boolean updateExpense(int id, String description, double amount,
                                  Expense.Category category, String date) {
        Expense existing = searchExpense(id);
        if (existing == null) {
            return false;
        }
        existing.setDescription(description);
        existing.setAmount(amount);
        existing.setCategory(category);
        existing.setDate(date);
        return true;
    }

    // DELETE
    public boolean deleteExpense(int id) {
        Expense existing = searchExpense(id);
        if (existing == null) {
            return false;
        }
        expenses.remove(existing);
        return true;
    }

    // Calculates the total amount spent across all expenses
    public double getTotalSpending() {
        double total = 0.0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }
}