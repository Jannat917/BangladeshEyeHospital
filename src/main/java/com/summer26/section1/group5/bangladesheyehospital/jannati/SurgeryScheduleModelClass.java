package com.summer26.section1.group5.bangladesheyehospital.jannati;



import java.io.Serializable;

public class SurgeryScheduleModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private int surgeryId;
    private int patientId;
    private String patientName;

    private int doctorId;
    private String doctorName;

    private String surgeryType;
    private String operationRoom;

    private String surgeryDate;
    private String surgeryTime;

    private String status;   // Scheduled, Completed, Cancelled

    public SurgeryScheduleModelClass() {
    }

    public SurgeryScheduleModelClass(
            int surgeryId,
            int patientId,
            String patientName,
            int doctorId,
            String doctorName,
            String surgeryType,
            String operationRoom,
            String surgeryDate,
            String surgeryTime,
            String status) {

        this.surgeryId = surgeryId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.surgeryType = surgeryType;
        this.operationRoom = operationRoom;
        this.surgeryDate = surgeryDate;
        this.surgeryTime = surgeryTime;
        this.status = status;
    }

    public int getSurgeryId() {
        return surgeryId;
    }

    public void setSurgeryId(int surgeryId) {
        this.surgeryId = surgeryId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(String surgeryType) {
        this.surgeryType = surgeryType;
    }

    public String getOperationRoom() {
        return operationRoom;
    }

    public void setOperationRoom(String operationRoom) {
        this.operationRoom = operationRoom;
    }

    public String getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(String surgeryDate) {
        this.surgeryDate = surgeryDate;
    }

    public String getSurgeryTime() {
        return surgeryTime;
    }

    public void setSurgeryTime(String surgeryTime) {
        this.surgeryTime = surgeryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SurgeryScheduleModelClass{" +
                "surgeryId=" + surgeryId +
                ", patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", surgeryType='" + surgeryType + '\'' +
                ", operationRoom='" + operationRoom + '\'' +
                ", surgeryDate='" + surgeryDate + '\'' +
                ", surgeryTime='" + surgeryTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}