package com.example.examen;

import com.example.examen.BD.BD;
import com.example.examen.lib.Utilis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {
    private BD bd = new BD();
    private Connection conn;
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = bd.getConnection();
        // mettre le button btnSignIn avec un fond #4b5279 et un texte blanc

    }
    @FXML
    void login(ActionEvent event) throws IOException {
        String login = loginInput.getText();
        String password = passwordInput.getText();
        if (conn == null) {
            System.out.println("Connection object is null");
            return;
        }

        // verifier si le login et le mot de passe sont corrects avec ceux de la table utilisateurs
        try {
            String query = "SELECT * FROM utilisateurs WHERE login = ? AND password = ?";
            java.sql.PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Utilis.loadScene(event, "principal.fxml", "Principal");
            } else {
                // afficher un alerte pour dire que le login ou le mot de passe est incorrect
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de connexion");
                alert.setHeaderText(null);
                alert.setContentText("Le login ou le mot de passe est incorrect.");
                alert.showAndWait();
                System.out.println("Connexion échouée");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
