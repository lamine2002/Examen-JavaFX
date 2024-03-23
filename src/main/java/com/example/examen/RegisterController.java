package com.example.examen;

import com.example.examen.BD.BD;
import com.example.examen.lib.Users;
import com.example.examen.lib.Utilis;
import com.example.examen.repository.UsersRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    private BD bd = new BD();

    private Connection conn;

    private UsersRepository usersRepository;
    @FXML
    private Button btnRegister;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField confirmInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField loginInput;

    @FXML
    private TextField mdpInput;

    @FXML
    private TextField nomInput;

    @FXML
    private TextField prenomInput;

    @FXML
    private TextField telInput;

    @FXML
    private Text welcomeText;

    @FXML
    void register(ActionEvent event) throws IOException {
        String nom = nomInput.getText();
        String prenom = prenomInput.getText();
        String email = emailInput.getText();
        String tel = telInput.getText();
        String login = loginInput.getText();
        String mdp = mdpInput.getText();
        String confirm = confirmInput.getText();

        nomInput.setText("");
        prenomInput.setText("");
        emailInput.setText("");
        telInput.setText("");
        loginInput.setText("");
        mdpInput.setText("");
        confirmInput.setText("");

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || tel.isEmpty() || login.isEmpty() || mdp.isEmpty() || confirm.isEmpty()) {
            Utilis.alertError("Veuillez remplir tous les champs");
            return;
        }

        if (!mdp.equals(confirm)) {
            Utilis.alertError("Les mots de passe ne correspondent pas");
            return;
        }
        Users user = new Users(nom, prenom, tel, email, login, mdp);
        usersRepository.addUser(user);
        if (usersRepository.checkUser(user.getLogin(), user.getPassword())) {
            Utilis.alert("Utilisateur enregistré avec succès");
            Utilis.loadScene(event, "connexion.fxml", "Connexion");
        } else {
            Utilis.alertError("Erreur lors de l'enregistrement de l'utilisateur");
        }
    }

    @FXML
    void toConnection(ActionEvent event) throws IOException {
        Utilis.loadScene(event, "connexion.fxml", "Connexion");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = bd.getConnection();
        usersRepository = new UsersRepository();
    }
}
