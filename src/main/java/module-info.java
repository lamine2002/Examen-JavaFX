module com.example.examen {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.examen to javafx.fxml;
    exports com.example.examen;
}