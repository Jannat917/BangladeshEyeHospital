package com.summer26.section1.group5.bangladesheyehospital.jannati;

import java.io.Serializable;
import java.time.LocalDate;

public class DiagnosticReportModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private int patientId;
    private String patientName;

    private LocalDate reportDate;
    private String testName;

    private String diagnosis;
    private String testResult;
    private String doctorRemarks;

    private String doctorName;

    public DiagnosticReportModelClass() {
    }

    public DiagnosticReportModelClass(int patientId,
                                      String patientName,
                                      LocalDate reportDate,
                                      String testName,
                                      String diagnosis,
                                      String testResult,
                                      String doctorRemarks,
                                      String doctorName) {

        this.patientId = patientId;
        this.patientName = patientName;
        this.reportDate = reportDate;
        this.testName = testName;
        this.diagnosis = diagnosis;
        this.testResult = testResult;
        this.doctorRemarks = doctorRemarks;
        this.doctorName = doctorName;
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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getDoctorRemarks() {
        return doctorRemarks;
    }

    public void setDoctorRemarks(String doctorRemarks) {
        this.doctorRemarks = doctorRemarks;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Override
    public String toString() {
        return "DiagnosticReportModelClass{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", reportDate=" + reportDate +
                ", testName='" + testName + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", testResult='" + testResult + '\'' +
                ", doctorRemarks='" + doctorRemarks + '\'' +
                ", doctorName='" + doctorName + '\'' +
                '}';
    }
}