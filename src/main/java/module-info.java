module com.summer26.section1.group5.bangladesheyehospital {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    exports com.summer26.section1.group5.bangladesheyehospital;
    opens com.summer26.section1.group5.bangladesheyehospital to javafx.fxml;
    exports com.summer26.section1.group5.bangladesheyehospital.common;
    opens com.summer26.section1.group5.bangladesheyehospital.common to javafx.fxml;
    exports com.summer26.section1.group5.bangladesheyehospital.jannati;
    opens com.summer26.section1.group5.bangladesheyehospital.jannati to javafx.fxml;
    exports com.summer26.section1.group5.bangladesheyehospital.nisa;
    opens com.summer26.section1.group5.bangladesheyehospital.nisa to javafx.fxml;
    exports com.summer26.section1.group5.bangladesheyehospital.mashrif;
    opens com.summer26.section1.group5.bangladesheyehospital.mashrif to javafx.fxml;


}