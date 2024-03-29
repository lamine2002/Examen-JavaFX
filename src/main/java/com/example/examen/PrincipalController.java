package com.example.examen;

import com.example.examen.lib.Product;
import com.example.examen.lib.Utilis;
import com.example.examen.repository.ProductRepository;
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
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {


    @FXML
    private Button UserNav;

    @FXML
    private Button categoryNav;

    @FXML
    private Button closeNav;

    @FXML
    private Button homeNav;

    @FXML
    private Pane paneContainer;

    @FXML
    private AnchorPane principalBody;

    @FXML
    private Pane principalPaneSideBar;

    @FXML
    private Button produitNav;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("home.fxml"));
            paneContainer.getChildren().removeAll();
            paneContainer.getChildren().setAll(fxml);
            // alerte dés que l’utilisateur se connecte
            //une liste des produits donc la quantité est inférieur à 5.
            ProductRepository productRepository = new ProductRepository();
            List<Product> products = productRepository.getAll();
            boolean alert = false;
            for (Product product : products) {
                if (product.getQuantite() < 5 && !alert) {
                    Utilis.alert("Le produit " + product.getLibelle() + " est en rupture de stock");
                }
            }
            alert = true;
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

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

    @FXML
    void linkToProduct(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("product.fxml"));
            paneContainer.getChildren().removeAll();
            paneContainer.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void linkToUser(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("users.fxml"));
            paneContainer.getChildren().removeAll();
            paneContainer.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
