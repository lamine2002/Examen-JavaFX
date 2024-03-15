package com.example.examen;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private Button aboutNav;

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
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void linkToAbout(ActionEvent event) {

    }

    @FXML
    void linkToHome(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("home.fxml"));
        paneContainer.getChildren().removeAll();
        paneContainer.getChildren().setAll(fxml);
    }
}
