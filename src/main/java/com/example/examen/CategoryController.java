package com.example.examen;

import com.example.examen.lib.Category;
import com.example.examen.repository.CategoryRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {
    private CategoryRepository categoryRepository;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLibelle;

    @FXML
    private Button btnAddCategorie;

    @FXML
    private Button btnClearCategorie;

    @FXML
    private Button btnEditCategorie;

    @FXML
    private Button btnDeleteCategorie;

    @FXML
    private TextField libelleInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryRepository = new CategoryRepository();
        displayCategories();

    }

    public void displayCategories() {
        ObservableList<Category> categories = categoryRepository.getAll();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        categoryTable.setItems(categories);

    }

    @FXML
    void addCategorie(ActionEvent event) {
        if (libelleInput.getText().isEmpty()) {
            // afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Le libellé ne doit pas être vide");
            alert.showAndWait();

        }else {
            Category category = new Category(libelleInput.getText());
            categoryRepository.addCategory(category);
            displayCategories();
            libelleInput.clear();
        }

    }

    @FXML
    void clear(ActionEvent event) {
        libelleInput.clear();
    }


    @FXML
    void updateCategorie(ActionEvent event) {
        Category category = categoryTable.getSelectionModel().getSelectedItem();
        category.setLibelle(libelleInput.getText());
        categoryRepository.updateCategory(category);
        displayCategories();
        libelleInput.clear();
    }

    @FXML
    void deleteCategorie(ActionEvent event) {
        // alerte de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression d'une catégorie");
        alert.setContentText("Voulez-vous vraiment supprimer cette catégorie ?");
        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait();
        if (alert.getResult() == buttonTypeYes) {
            Category category = categoryTable.getSelectionModel().getSelectedItem();
            categoryRepository.deleteCategory(category.getId());
            displayCategories();
        }

    }
    @FXML
    void editCategorie(javafx.scene.input.MouseEvent event) {
        if (event.getClickCount() == 2) {
            Category category = categoryTable.getSelectionModel().getSelectedItem();
            libelleInput.setText(category.getLibelle());
        }
    }
}
