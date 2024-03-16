package com.example.examen;

import com.example.examen.BD.BD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion.fxml"));

// Création de la scène sans spécifier la taille, pour qu'elle s'adapte au contenu FXML
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED); // Cette ligne permet de retirer la barre de titre
        stage.setResizable(false); // Vous pouvez retirer cette ligne si vous voulez permettre le redimensionnement
        stage.sizeToScene(); // Cette ligne ajuste la taille de la fenêtre à celle de la scène
        stage.centerOnScreen(); // Centre la fenêtre sur l'écran
        stage.show();

//        BD bd = new BD();
//        bd.getConnection();

    }

    public static void main(String[] args) {
        launch();
    }
}