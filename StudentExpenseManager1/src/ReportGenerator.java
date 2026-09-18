import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ReportGenerator.java
 *
 * Contains the logic to calculate spending reports:
 * total spending, average expense, highest expense,
 * category-wise totals, and monthly summary.
 * This class does the actual calculation work; ReportThread
 * calls into this class from within its run() method.
 */
public class ReportGenerator {

    private ArrayList<Expense> expenses;

    public ReportGenerator(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public double getTotalSpending() {
        double total = 0.0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    public double getAverageExpense() {
        if (expenses.isEmpty()) {
            return 0.0;
        }
        return getTotalSpending() / expenses.size();
    }

    public Expense getHighestExpense() {
        if (expenses.isEmpty()) {
            return null;
        }
        Expense highest = expenses.get(0);
        for (Expense e : expenses) {
            if (e.getAmount() > highest.getAmount()) {
                highest = e;
            }
        }
        return highest;
    }

    // Returns a map of category -> total amount spent in that category
    public Map<Expense.Category, Double> getCategoryWiseSpending() {
        Map<Expense.Category, Double> categoryTotals = new LinkedHashMap<>();
        for (Expense.Category c : Expense.Category.values()) {
            categoryTotals.put(c, 0.0);
        }
        for (Expense e : expenses) {
            double current = categoryTotals.get(e.getCategory());
            categoryTotals.put(e.getCategory(), current + e.getAmount());
        }
        return categoryTotals;
    }

    // Returns a map of "MM-yyyy" -> total amount spent in that month,
    // based on the dd-MM-yyyy date stored with each expense
    public Map<String, Double> getMonthlySummary() {
        Map<String, Double> monthlyTotals = new LinkedHashMap<>();
        for (Expense e : expenses) {
            String date = e.getDate();
            String monthKey = extractMonthYear(date);
            double current = monthlyTotals.getOrDefault(monthKey, 0.0);
            monthlyTotals.put(monthKey, current + e.getAmount());
        }
        return monthlyTotals;
    }

    // Extracts "MM-yyyy" out of a "dd-MM-yyyy" date string
    private String extractMonthYear(String date) {
        String[] parts = date.split("-");
        if (parts.length == 3) {
            return parts[1] + "-" + parts[2];
        }
        return "Unknown";
    }

    /**
     * Prints the full formatted report to the console.
     * This is the method that ReportThread invokes inside run().
     */
    public void printFullReport() {
        System.out.println("========================================");
        System.out.println("          EXPENSE REPORT");
        System.out.println("========================================");

        if (expenses.isEmpty()) {
            System.out.println("No expenses available to generate a report.");
            System.out.println("========================================");
            return;
        }

        System.out.printf("Total Spending      : Rs.%.2f%n", getTotalSpending());
        System.out.printf("Average Expense     : Rs.%.2f%n", getAverageExpense());

        Expense highest = getHighestExpense();
        System.out.printf("Highest Expense     : Rs.%.2f (%s - %s)%n",
                highest.getAmount(), highest.getDescription(), highest.getCategory());
        System.out.println("Highest Category    : " + highest.getCategory());

        System.out.println("----------------------------------------");
        System.out.println("Category-wise Spending:");
        for (Map.Entry<Expense.Category, Double> entry : getCategoryWiseSpending().entrySet()) {
            if (entry.getValue() > 0) {
                System.out.printf("  %-15s : Rs.%.2f%n", entry.getKey(), entry.getValue());
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("Monthly Spending Summary:");
        for (Map.Entry<String, Double> entry : getMonthlySummary().entrySet()) {
            System.out.printf("  %-10s : Rs.%.2f%n", entry.getKey(), entry.getValue());
        }

        System.out.println("========================================");
    }
}