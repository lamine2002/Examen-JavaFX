package com.example.examen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class ProductController {
    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnClearProduct;

    @FXML
    private Button btnDeleteProduct;

    @FXML
    private Button btnEditProduct;

    @FXML
    private ComboBox<?> categoryCombo;

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

}
