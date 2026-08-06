package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;
import java.time.LocalDate;

public class EyeglassOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private String prescriptionId;
    private String patientName;
    private String frameId;
    private String frameName;
    private LocalDate orderDate;
    private String status;
    private double totalAmount;

    public EyeglassOrder() {}

    public EyeglassOrder(String orderId, String prescriptionId, String patientName,
                         String frameId, String frameName, LocalDate orderDate,
                         String status, double totalAmount) {
        this.orderId = orderId;
        this.prescriptionId = prescriptionId;
        this.patientName = patientName;
        this.frameId = frameId;
        this.frameName = frameName;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(String prescriptionId) { this.prescriptionId = prescriptionId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getFrameId() { return frameId; }
    public void setFrameId(String frameId) { this.frameId = frameId; }
    public String getFrameName() { return frameName; }
    public void setFrameName(String frameName) { this.frameName = frameName; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    @Override
    public String toString() {
        return orderId + " - " + patientName;
    }
}
