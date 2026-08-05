package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class Feedback {

    private int patientId;
    private String feedback;
    private int rating;

    public Feedback(String feedback, int patientId, int rating) {
        this.feedback = feedback;
        this.patientId = patientId;
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedback='" + feedback + '\'' +
                ", patientId=" + patientId +
                ", rating=" + rating +
                '}';
    }
}
