module com.mycompany.proyectochat.cliente {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.proyectochat.cliente to javafx.fxml;
    exports com.mycompany.proyectochat.cliente;
}
