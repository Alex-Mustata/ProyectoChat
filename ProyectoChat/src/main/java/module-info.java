module com.mycompany.proyectochat {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.proyectochat to javafx.fxml;
    exports com.mycompany.proyectochat;
}
