package com.example.examen;

import com.example.examen.lib.Utilis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private Button btnCategory;

    @FXML
    private Button closeNav;

    @FXML
    private Button homeNav;

    @FXML
    private AnchorPane principalBody;

    @FXML
    private Pane principalPaneSideBar;

    @FXML
    private Pane paneContainer;

    @FXML
    void close(ActionEvent event) throws IOException {
        //System.exit(0); // ce code permet de fermer l'application de manière brutale
        Utilis.loadScene(event, "connexion.fxml", "Connexion");
    }

    @FXML
    void linkToCategory(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("category.fxml"));
            paneContainer.getChildren().removeAll();
            paneContainer.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void linkToHome(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("home.fxml"));
        paneContainer.getChildren().removeAll();
        paneContainer.getChildren().setAll(fxml);
    }
}
