package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class EmergencyAppointment {

    private int patientId;
    private String patientName;
    private String problem;

    public EmergencyAppointment(int patientId, String patientName, String problem) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.problem = problem;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "EmergencyAppointment{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", problem='" + problem + '\'' +
                '}';
    }
}
