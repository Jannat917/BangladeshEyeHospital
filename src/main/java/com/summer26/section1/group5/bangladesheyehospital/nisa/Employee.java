package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String designation;
    private double basicSalary;
    private String paymentStatus;

    public Employee(double basicSalary, String designation, int employeeId, String employeeName, String paymentStatus) {
        this.basicSalary = basicSalary;
        this.designation = designation;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.paymentStatus = paymentStatus;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "basicSalary=" + basicSalary +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", designation='" + designation + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}