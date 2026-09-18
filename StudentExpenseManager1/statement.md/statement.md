# Project Statement

## Problem Statement

Most students manage their daily expenses informally — mentally, or across
scattered notes — with no easy way to see how much they've spent against
what they planned to spend for the month. This makes it hard to notice
overspending until it has already happened, and makes it difficult to
understand spending patterns (e.g. which category eats up the most money,
or which month was more expensive than usual). Students need a simple,
lightweight tool that lets them log expenses as they occur, compare that
spending against a monthly budget, and review a summary when needed —
without requiring a phone app, internet connection, or account sign-up.

## Scope

This is a console-based application intended for use by a single student
at a time, running locally on their own computer. It uses file-based
storage (a plain text file) rather than a database or cloud service, which
keeps the project self-contained, easy to set up, and easy to explain in a
viva. The scope is limited to personal expense tracking and monthly budget
monitoring — it does not handle multi-user accounts, bank integration, or
recurring transactions.

## Target Users

Students who want a simple, no-frills way to track their personal daily
expenses and stay within a monthly budget.

## High-Level Features

- **Expense Management** — Add, view, search, update, and delete expense
  records, each with a description, amount, category, and date.
- **Budget Management** — Set a monthly budget and check spending against
  it, with a warning when the budget has been exceeded.
- **Reports** — Generate a spending report (total, average, highest
  expense, category-wise breakdown, monthly summary), calculated on a
  background thread.
- **File Storage** — Persist expense data between runs using a plain text
  file and Java character streams.
- **Input Validation** — Guard against invalid input (bad numbers,
  negative amounts, empty fields, duplicate IDs) so the application never
  crashes from normal user mistakes.
- **Multithreading** — Demonstrate real background-thread execution for
  report generation using `Thread`, `start()`, `sleep()`, and `join()`.
