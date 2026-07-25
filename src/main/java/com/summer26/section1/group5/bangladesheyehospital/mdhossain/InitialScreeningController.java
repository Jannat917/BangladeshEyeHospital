//package com.summer26.section1.group5.bangladesheyehospital.mdhossain;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//
//public class InitialScreeningController {
//    @FXML private TableView<PatientStub> waitingListTable;
//    @FXML private TableColumn<PatientStub, String> idCol;
//    @FXML private TableColumn<PatientStub, String> nameCol;
//    @FXML private TextField eyesightField;
//    @FXML private TextField bpField;
//    @FXML private Label statusLabel;
//
//    @FXML
//    private void handleSaveTriage() {
//        String score = eyesightField.getText();
//        String bp = bpField.getText();
//        if (score.isEmpty() || bp.isEmpty()) {
//            statusLabel.setText("Fill all fields");
//            return;
//        }
//        statusLabel.setText("Data Saved Successfully");
//    }
//
//    public static class PatientStub {
//        private String id;
//        private String name;
//        public PatientStub(String id, String name) { this.id = id; this.name = name; }
//        public String getId() { return id; }
//        public String getName() { return name; }
//    }
//}
