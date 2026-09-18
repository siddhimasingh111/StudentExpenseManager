# Student Expense & Budget Manager

## Project Overview

Student Expense & Budget Manager is a console-based Java application that
helps students record their daily expenses, set a monthly budget, monitor
their spending, and generate simple spending reports. All data is saved to
a text file so it is still available the next time the application is run.

This project was built for a **Programming in Java** course and is designed
to clearly demonstrate core Java and object-oriented programming concepts.

## Problem Being Solved

Many students don't track where their money goes each month. Without a
simple way to log expenses and compare them against a budget, it's easy to
overspend without realizing it. This application gives students a quick,
no-frills tool to log expenses as they happen, check remaining budget at a
glance, and review a spending report broken down by category and month.

## Features

- **Expense Management** — Add, view, search, update, and delete expenses
  (full CRUD).
- **Categorized Expenses** — Each expense is tagged with a category
  (FOOD, TRAVEL, SHOPPING, EDUCATION, ENTERTAINMENT, OTHER) using a Java enum.
- **Budget Management** — Set a monthly budget, view total spending,
  calculate remaining budget, and get a warning when the budget is exceeded.
- **Spending Reports** — Total spending, average expense, highest expense,
  category-wise totals, and a monthly spending summary. Report generation
  runs on a separate background thread.
- **Persistent File Storage** — Expenses are saved to `data/expenses.txt`
  using character streams, and reloaded automatically on startup.
- **Input Validation** — A dedicated validator class prevents the
  application from crashing on bad input (non-numeric choices, negative
  amounts, empty fields, duplicate IDs, invalid categories, corrupted file
  records, etc.).

## Java Concepts Used

- Classes and Objects
- Encapsulation (private fields with public getters/setters)
- Constructors
- Methods
- ArrayList / Collections (including `Map` for report grouping)
- Enum (`Expense.Category`)
- CRUD operations
- Exception Handling (`try-catch`, custom validation flows)
- File Handling with Character Streams
  (`FileReader`, `FileWriter`, `BufferedReader`, `BufferedWriter`)
- Multithreading (`Thread`, `start()`, `sleep()`, `join()`)
- Method Overriding (`toString()` in `Expense`)
- Modular Programming (responsibilities split across 10 focused classes)

## Technologies / Tools

- Java (JDK 8 or higher — no external libraries required)
- Standard Java SE class library only
- Plain text file storage (no database required)

## Project Structure

```
StudentExpenseManager/
│
├── src/
│   ├── Main.java             # Entry point
│   ├── Menu.java              # Console menu and controller logic
│   ├── User.java               # Student/user info
│   ├── Expense.java            # Expense model + Category enum
│   ├── Budget.java             # Budget tracking logic
│   ├── ExpenseManager.java     # CRUD operations on expenses
│   ├── ReportGenerator.java    # Report calculations
│   ├── FileManager.java        # File save/load (character streams)
│   ├── InputValidator.java     # Centralized input validation
│   └── ReportThread.java       # Background thread for report generation
│
├── data/
│   └── expenses.txt            # Saved expense data (auto-created)
│
├── screenshots/                # Place test/demo screenshots here
│
├── README.md
├── statement.md
└── .gitignore
```

## How to Compile

From the `StudentExpenseManager` project root:

```bash
javac -d bin src/*.java
```

Or, to compile without a separate output folder (class files land in `src`):

```bash
javac src/*.java
```

## How to Run

If you compiled with `javac src/*.java`:

```bash
java -cp src Main
```

If you compiled with `javac -d bin src/*.java`:

```bash
java -cp bin Main
```

> **Important:** Run the `java` command from the `StudentExpenseManager`
> project root (the folder containing `src/` and `data/`), so the
> application can correctly find/create `data/expenses.txt`.

## How to Test

1. Run the application using the steps above.
2. Use the menu to add a few expenses across different categories.
3. Try invalid inputs on purpose (letters instead of numbers, negative
   amounts, empty description, a duplicate ID) and confirm you get a clear
   error message instead of a crash.
4. Set a monthly budget lower than your total spending and confirm the
   "budget exceeded" warning appears.
5. Choose **Generate Report** and confirm the totals, averages, highest
   expense, category breakdown, and monthly summary look correct.
6. Choose **Save Data**, then **Exit**.
7. Restart the application and confirm your expenses were reloaded from
   `data/expenses.txt`.

## Data Storage

Expenses are stored as plain text in `data/expenses.txt`, one record per
line, in the format:

```
ID|Description|Amount|Category|Date
```

Example:

```
1|Lunch|120.0|FOOD|15-09-2026
2|Bus Pass|450.0|TRAVEL|16-09-2026
```

The file is automatically created (along with the `data/` folder, if
missing) the first time expenses are saved. If a line in the file is
malformed or corrupted, it is safely skipped while loading, and the rest
of the file still loads normally.

## Future Improvements

- Support multiple users/profiles with separate expense files.
- Add date-range filtering when viewing expenses.
- Export reports to a separate summary text/CSV file.
- Add recurring/monthly-fixed expenses.
- Migrate storage from a text file to a lightweight database (e.g. SQLite).
