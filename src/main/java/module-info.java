module com.summer26.section1.group5.bangladesheyehospital {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.summer26.section1.group5.bangladesheyehospital.mashrif to javafx.graphics, javafx.fxml;
    opens com.summer26.section1.group5.bangladesheyehospital to javafx.fxml;
    exports com.summer26.section1.group5.bangladesheyehospital;
}