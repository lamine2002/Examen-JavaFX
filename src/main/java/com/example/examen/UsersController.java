package com.example.examen;

import com.example.examen.lib.Users;
import com.example.examen.lib.Utilis;
import com.example.examen.repository.UsersRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    private UsersRepository usersRepository;
    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnClearUser;

    @FXML
    private Button btnEditUser;

    @FXML
    private Button btnDeleteUser;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLogin;

    @FXML
    private TableColumn<?, ?> colNom;

    @FXML
    private TableColumn<?, ?> colPrenom;

    @FXML
    private TableColumn<?, ?> colTelephone;

    @FXML
    private TableColumn<?, ?> colmdp;

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
    private TextField telephoneInput;

    @FXML
    private TableView<Users> userTable;

    @FXML
    void addUser(ActionEvent event) {
        if (nomInput.getText().isEmpty() || prenomInput.getText().isEmpty() || telephoneInput.getText().isEmpty() || emailInput.getText().isEmpty() || loginInput.getText().isEmpty() || mdpInput.getText().isEmpty()) {
            Utilis.alertError("Veuillez remplir tous les champs");
        }else {
            String nom = nomInput.getText();
            String prenom = prenomInput.getText();
            String telephone = telephoneInput.getText();
            String email = emailInput.getText();
            String login = loginInput.getText();
            String password = mdpInput.getText();
            Users user = new Users(nom, prenom, telephone, email, login, password);
            usersRepository.addUser(user);
            clear(event);
            displayUsers();
        }

    }

    @FXML
    void clear(ActionEvent event) {
        nomInput.setText("");
        prenomInput.setText("");
        telephoneInput.setText("");
        emailInput.setText("");
        loginInput.setText("");
        mdpInput.setText("");
    }

    @FXML
    void editUser(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Users user = userTable.getSelectionModel().getSelectedItem();
            nomInput.setText(user.getNom());
            prenomInput.setText(user.getPrenom());
            telephoneInput.setText(user.getTelephone());
            emailInput.setText(user.getEmail());
            loginInput.setText(user.getLogin());
            mdpInput.setText(user.getPassword());
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        if (nomInput.getText().isEmpty() || prenomInput.getText().isEmpty() || telephoneInput.getText().isEmpty() || emailInput.getText().isEmpty() || loginInput.getText().isEmpty() || mdpInput.getText().isEmpty()) {
            Utilis.alertError("Veuillez remplir tous les champs");
        }else {
            Users user = userTable.getSelectionModel().getSelectedItem();
            user.setNom(nomInput.getText());
            user.setPrenom(prenomInput.getText());
            user.setTelephone(telephoneInput.getText());
            user.setEmail(emailInput.getText());
            user.setLogin(loginInput.getText());
            user.setPassword(mdpInput.getText());
            usersRepository.updateUser(user);
            clear(event);
            displayUsers();
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        boolean result = Utilis.alertConfirmation("Voulez-vous vraiment supprimer cet utilisateur?");
        if (result) {
            Users user = userTable.getSelectionModel().getSelectedItem();
            usersRepository.deleteUser(user.getId());
            displayUsers();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersRepository = new UsersRepository();
        displayUsers();
    }

    public void displayUsers() {
        ObservableList<Users> users = usersRepository.getAllUsers();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colmdp.setCellValueFactory(new PropertyValueFactory<>("password"));
        userTable.setItems(users);
    }
}
