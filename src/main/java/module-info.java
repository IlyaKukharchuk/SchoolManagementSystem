module com.example.scmgsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.scmgsys to javafx.fxml;
    opens com.example.scmgsys.studentPhotos to javafx.fxml;
    opens com.example.scmgsys.login to javafx.fxml;

    exports com.example.scmgsys;
    exports com.example.scmgsys.dbUtil;
    exports com.example.scmgsys.login;
}