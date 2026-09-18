import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * FileManager.java
 *
 * Handles saving and loading expense data to/from a text file
 * using character streams.
 * Demonstrates: File Handling, Character Streams
 * (FileReader/FileWriter/BufferedReader/BufferedWriter), Exception Handling.
 */
public class FileManager {

    private String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given list of expenses to the data file.
     * Each expense is written using its pipe-separated file format.
     */
    public void saveExpenses(ArrayList<Expense> expenses) {
        // Make sure the parent "data" folder exists before writing
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Expense e : expenses) {
                writer.write(e.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    /**
     * Loads expenses from the data file. If the file does not exist yet
     * (first run), returns an empty list instead of throwing an error.
     * Any corrupted/invalid line is skipped safely so the rest of the
     * file can still be loaded.
     */
    public ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return expenses; // nothing to load yet, that's fine
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Expense expense = parseLine(line);
                if (expense != null) {
                    expenses.add(expense);
                }
                // if parseLine returns null, the record was invalid/corrupted
                // and is simply skipped so loading can continue safely
            }
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }

        return expenses;
    }

    /**
     * Parses a single line in "ID|Description|Amount|Category|Date" format
     * into an Expense object. Returns null if the line is malformed.
     */
    private Expense parseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            return null; // corrupted/invalid record
        }
        try {
            int id = Integer.parseInt(parts[0].trim());
            String description = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());
            Expense.Category category = Expense.Category.valueOf(parts[3].trim());
            String date = parts[4].trim();

            if (description.isEmpty() || date.isEmpty() || amount <= 0) {
                return null;
            }
            return new Expense(id, description, amount, category, date);
} catch (IllegalArgumentException e) {
            // covers bad integer/double parsing and invalid enum values
            return null;
        }
    }
}