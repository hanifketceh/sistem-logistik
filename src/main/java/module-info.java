module com.mycompany.logistik {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.logistik to javafx.fxml;
    exports com.mycompany.logistik;
}
