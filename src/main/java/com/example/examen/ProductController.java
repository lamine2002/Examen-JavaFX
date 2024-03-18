package com.example.examen;

import com.example.examen.lib.Category;
import com.example.examen.lib.Product;
import com.example.examen.lib.Utilis;
import com.example.examen.repository.CategoryRepository;
import com.example.examen.repository.ProductRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    @FXML
    private Button btnAddProduct;
    @FXML
    private Button btnPDFProduct;

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

    @FXML
    void PDFProduct(ActionEvent event) {
        Document document = new Document();
        BaseColor themeColor = WebColors.getRGBColor("#353A56"); // Couleur du thème
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/files/Products.pdf"));
            document.open();

            // Ajouter un titre
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            titleFont.setColor(themeColor); // Appliquer la couleur du thème au titre
            Paragraph title = new Paragraph("Liste des produits", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter un espace
            document.add(new Paragraph(" "));

            // Créer un tableau
            PdfPTable table = new PdfPTable(5); // 5 colonnes
            table.setWidthPercentage(100); // Largeur 100%
            table.setSpacingBefore(10f); // Espace avant le tableau
            table.setSpacingAfter(10f); // Espace après le tableau

            // Définir les en-têtes de colonne avec la couleur du thème
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE); // Texte blanc pour contraste
            String[] columnHeaders = new String[]{"ID", "Libelle", "Prix", "Quantite", "Categorie"};
            for (String columnHeader : columnHeaders) {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(themeColor); // Fond avec la couleur du thème
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnHeader, headerFont));
                table.addCell(header);
            }

            // Ajouter les données du produit
            List<Product> products = productRepository.getAll();
            for (Product product : products) {
                table.addCell(String.valueOf(product.getId()));
                table.addCell(product.getLibelle());
                table.addCell(String.valueOf(product.getPrixUnitaire()));
                table.addCell(String.valueOf(product.getQuantite()));
                table.addCell(product.getCategorie());
            }

            // Ajouter le tableau au document
            document.add(table);

            // Ajouter un pied de page avec un numéro de page (optionnel)
            writer.setPageEvent(new PdfPageEventHelper() {
                public void onEndPage(PdfWriter writer, Document document) {
                    PdfContentByte cb = writer.getDirectContent();
                    cb.beginText();
                    BaseFont bf = null;
                    try {
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cb.setFontAndSize(bf, 10);
                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Page " + document.getPageNumber(), (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 20, 0);
                    cb.endText();
                }
            });

            document.close();
            Utilis.alert("Le fichier PDF a été généré avec succès.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}
