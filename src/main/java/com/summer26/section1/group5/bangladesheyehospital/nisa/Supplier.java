package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class Supplier {

    private int supplierId;
    private String supplierName;
    private int invoiceNumber;
    private double dueAmount;
    private String paymentMethod;
    private String paymentStatus;

    public Supplier(double dueAmount, int invoiceNumber, String paymentMethod, String paymentStatus, int supplierId, String supplierName) {
        this.dueAmount = dueAmount;
        this.invoiceNumber = invoiceNumber;
        this.paymentMethod = "";
        this.paymentStatus = "Due";
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "dueAmount=" + dueAmount +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", invoiceNumber=" + invoiceNumber +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}


