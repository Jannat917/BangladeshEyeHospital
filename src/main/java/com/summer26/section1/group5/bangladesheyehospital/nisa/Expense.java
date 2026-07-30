package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class Expense {
    private int expenseId;
    private String expenseType;
    private double expense;
    private double income;

    public Expense(double expense, int expenseId, String expenseType, double income) {
        this.expense = expense;
        this.expenseId = expenseId;
        this.expenseType = expenseType;
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
    public double getProfit() {
        return income - expense;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expense=" + expense +
                ", expenseId=" + expenseId +
                ", expenseType='" + expenseType + '\'' +
                ", income=" + income +
                '}';
    }
}
