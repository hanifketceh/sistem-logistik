module com.mycompany.logistik {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.logistik to javafx.fxml;
    opens com.mycompany.logistik.controller to javafx.fxml;
    opens com.mycompany.logistik.model to javafx.base;

    exports com.mycompany.logistik;
    exports com.mycompany.logistik.model;
    exports com.mycompany.logistik.service;
    exports com.mycompany.logistik.util;
    exports com.mycompany.logistik.controller;
}

