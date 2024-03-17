package com.example.examen;

import com.example.examen.lib.Category;
import com.example.examen.lib.Product;
import com.example.examen.lib.Utilis;
import com.example.examen.repository.CategoryRepository;
import com.example.examen.repository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
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
    private TableColumn<?, ?> colCategorie;

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
    private TableView<Product> productTable;

    @FXML
    private TextField quantiteInput;

    @FXML
    void addProduct(ActionEvent event) {
        // verifier si les champs sont vides
        if (libelleInput.getText().isEmpty() || prixInput.getText().isEmpty() || quantiteInput.getText().isEmpty()) {
            Utilis.alertError("Veuillez remplir tous les champs");
        } else {
            // recuperer les valeurs des champs
            String libelle = libelleInput.getText();
            double prix = Double.parseDouble(prixInput.getText());
            int quantite = Integer.parseInt(quantiteInput.getText());
            String categorie = categoryCombo.getValue();
            // creer un objet de type Product
            Product product = new Product();
            product.setLibelle(libelle);
            product.setPrixUnitaire(prix);
            product.setQuantite(quantite);
            product.setCategorie(categorie);
            // ajouter le produit dans la base de donnees
            productRepository.addProduct(product);
            // vider les champs
            clear(event);
            // actualiser le tableau
            displayProducts();
        }
    }

    @FXML
    void clear(ActionEvent event) {
        libelleInput.clear();
        prixInput.clear();
        quantiteInput.clear();
        categoryCombo.setValue("");
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        boolean result = Utilis.alertConfirmation("Voulez-vous vraiment supprimer ce produit?");
        if (result) {
            Product product = productTable.getSelectionModel().getSelectedItem();
            productRepository.deleteProduct(product.getId());
            displayProducts();
        }
    }

    @FXML
    void editProduct(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Product product = productTable.getSelectionModel().getSelectedItem();
            libelleInput.setText(product.getLibelle());
            prixInput.setText(String.valueOf(product.getPrixUnitaire()));
            quantiteInput.setText(String.valueOf(product.getQuantite()));
            categoryCombo.setValue(product.getCategorie());
        }
    }

    @FXML
    void updateProduct(ActionEvent event) {
        if (libelleInput.getText().isEmpty() || prixInput.getText().isEmpty() || quantiteInput.getText().isEmpty()) {
            Utilis.alertError("Veuillez remplir tous les champs");
            return;
        }else {
            Product product = productTable.getSelectionModel().getSelectedItem();
            product.setLibelle(libelleInput.getText());
            product.setPrixUnitaire(Double.parseDouble(prixInput.getText()));
            product.setQuantite(Integer.parseInt(quantiteInput.getText()));
            product.setCategorie(categoryCombo.getValue());
            productRepository.updateProduct(product);
            displayProducts();
            clear(event);
        }
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
        productRepository = new ProductRepository();
        categoryRepository = new CategoryRepository();
        displayProducts();

    }

    public void displayProducts() {
        ObservableList<Product> products = productRepository.getAll();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        productTable.setItems(products);
    }
}
