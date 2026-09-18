/**
 * Expense.java
 *
 * Represents a single expense record.
 * Demonstrates: Classes/Objects, Encapsulation, Constructors,
 * Getters/Setters, Enum, and Method Overriding (toString).
 */
public class Expense {

    // Enum for expense categories - kept inside Expense.java as required
    public enum Category {
        FOOD,
        TRAVEL,
        SHOPPING,
        EDUCATION,
        ENTERTAINMENT,
        OTHER
    }

    // Private fields -> encapsulation
    private int id;
    private String description;
    private double amount;
    private Category category;
    private String date; // stored as String in dd-MM-yyyy format for simplicity

    // Constructor to initialize a new expense object
    public Expense(int id, String description, double amount, Category category, String date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // ---------- Getters and Setters ----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Converts this expense into the pipe-separated line format used
     * for file storage: ID|Description|Amount|Category|Date
     */
    public String toFileFormat() {
        return id + "|" + description + "|" + amount + "|" + category + "|" + date;
    }

    // Method overriding - overrides Object's toString()
    @Override
    public String toString() {
        return String.format("ID: %-4d | %-15s | Rs.%-10.2f | %-13s | %s",
                id, description, amount, category, date);
    }
}