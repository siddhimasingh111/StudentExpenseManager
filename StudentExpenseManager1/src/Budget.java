/**
 * Budget.java
 *
 * Stores the monthly budget and provides methods to check
 * remaining budget and whether the budget has been exceeded.
 * Demonstrates: Encapsulation, Constructors, Methods.
 */
public class Budget {

    private double monthlyBudget;

    // Default constructor - budget starts at 0 until the user sets it
    public Budget() {
        this.monthlyBudget = 0.0;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    // Calculates how much budget is left given total spending so far
    public double getRemainingBudget(double totalSpent) {
        return monthlyBudget - totalSpent;
    }

    // Returns true if total spending has crossed the monthly budget
    public boolean isBudgetExceeded(double totalSpent) {
        return totalSpent > monthlyBudget;
    }

    // Prints a formatted summary of the budget status
    public void displayBudgetStatus(double totalSpent) {
        System.out.println("----------------------------------------");
        System.out.printf("Monthly Budget : Rs.%.2f%n", monthlyBudget);
        System.out.printf("Total Spent    : Rs.%.2f%n", totalSpent);

        double remaining = getRemainingBudget(totalSpent);
        if (remaining >= 0) {
            System.out.printf("Remaining      : Rs.%.2f%n", remaining);
        } else {
            System.out.printf("Over Budget By : Rs.%.2f%n", Math.abs(remaining));
        }

        if (isBudgetExceeded(totalSpent)) {
            System.out.println("WARNING: You have exceeded your monthly budget!");
        }
        System.out.println("----------------------------------------");
    }
}