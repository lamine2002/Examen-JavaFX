package com.example.examen;

import com.example.examen.lib.Category;
import com.example.examen.lib.Product;
import com.example.examen.lib.Utilis;
import com.example.examen.repository.CategoryRepository;
import com.example.examen.repository.ProductRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private Button btnProductInCategory;

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
            Utilis.alertError("Le libellé est obligatoire");

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
        boolean result = Utilis.alertConfirmation("Voulez-vous vraiment supprimer cette catégorie ?");
        if (result) {
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

    @FXML
    void excelNumberOfProductPerCategory(ActionEvent event) {
        ProductRepository productRepository = new ProductRepository();
        ObservableList<Product> products = productRepository.getNombreProduitParCategorie();

        // Créer un nouveau classeur Excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Nombre de produits par catégorie");

        // Créer l'en-tête de la feuille Excel
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Catégorie");
        header.createCell(1).setCellValue("Nombre de produits");

        // Écrire les données dans la feuille Excel
        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getCategorie());
            row.createCell(1).setCellValue(product.getQuantite());
        }

        // Écrire le classeur dans un fichier
        try (FileOutputStream outputStream = new FileOutputStream("src/main/resources/files/NombreDeProduitsParCategorie.xlsx")) {
            workbook.write(outputStream);
            Utilis.alert("Le fichier Excel a été sauvegardé avec succès dans le répertoire resources/files.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
