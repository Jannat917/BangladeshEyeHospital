package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class DoctorSchedule {
    private int doctorId;
    private String doctorName;
    private String department;
    private String day;
    private String time;

    public DoctorSchedule(String day, String department, int doctorId, String doctorName, String time) {
        this.day = day;
        this.department = department;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DoctorSchedule{" +
                "day='" + day + '\'' +
                ", doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", department='" + department + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
