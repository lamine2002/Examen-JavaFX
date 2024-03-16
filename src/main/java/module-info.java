module com.example.examen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;


    opens com.example.examen to javafx.fxml;
    exports com.example.examen;
    exports com.example.examen.lib;
    opens com.example.examen.lib to javafx.fxml;
    exports com.example.examen.BD;
    opens com.example.examen.BD to javafx.fxml;

}