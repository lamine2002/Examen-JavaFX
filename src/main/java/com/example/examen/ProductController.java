package com.example.examen;

import com.example.examen.lib.Category;
import com.example.examen.repository.CategoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnClearProduct;

    @FXML
    private Button btnDeleteProduct;

    @FXML
    private Button btnEditProduct;

    @FXML
    private ComboBox<String> categoryCombo;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLibelle;

    @FXML
    private TableColumn<?, ?> colPrix;

    @FXML
    private TableColumn<?, ?> colQuantite;

    @FXML
    private TextField libelleInput;

    @FXML
    private TextField prixInput;

    @FXML
    private TableView<?> productTable;

    @FXML
    private TextField quantiteInput;

    @FXML
    void addProduct(ActionEvent event) {

    }

    @FXML
    void clear(ActionEvent event) {

    }

    @FXML
    void deleteProduct(ActionEvent event) {

    }

    @FXML
    void editProduct(MouseEvent event) {

    }

    @FXML
    void updateProduct(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // implementer le combo avec les libelles categories
        CategoryRepository categoryRepository = new CategoryRepository();
        // recuperer les libelles des categories dans une liste
        ObservableList<String> libelles = FXCollections.observableArrayList();
        List<Category> categories = categoryRepository.getAll();
        for (Category category : categories) {
            libelles.add(category.getLibelle());
        }
        // ajouter les libelles dans le combo
        categoryCombo.setItems(libelles);



    }
}
