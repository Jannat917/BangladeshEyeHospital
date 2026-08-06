module com.summer26.section1.group5.bangladesheyehospital {

    requires javafx.controls;
    requires javafx.fxml;

    opens com.summer26.section1.group5.bangladesheyehospital
            to javafx.fxml;

    opens com.summer26.section1.group5.bangladesheyehospital.common
            to javafx.fxml;

    opens com.summer26.section1.group5.bangladesheyehospital.jannati
            to javafx.fxml;

    opens com.summer26.section1.group5.bangladesheyehospital.nisa
            to javafx.fxml;

    opens com.summer26.section1.group5.bangladesheyehospital.mashrif
            to javafx.fxml;

    opens com.summer26.section1.group5.bangladesheyehospital.mdhossain
            to javafx.fxml;

    exports com.summer26.section1.group5.bangladesheyehospital.common;
    exports com.summer26.section1.group5.bangladesheyehospital;
    exports com.summer26.section1.group5.bangladesheyehospital.jannati;
    exports com.summer26.section1.group5.bangladesheyehospital.nisa;
    exports com.summer26.section1.group5.bangladesheyehospital.mashrif;
    exports com.summer26.section1.group5.bangladesheyehospital.mdhossain;
}