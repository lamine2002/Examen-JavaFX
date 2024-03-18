package com.example.examen.repository;

import com.example.examen.BD.BD;
import com.example.examen.lib.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductRepository {
    private Connection conn;
    private CategoryRepository categoryRepository;
    public ProductRepository() {
        this.conn = new BD().getConnection();
        categoryRepository = new CategoryRepository();
    }

    public void addProduct(Product product) {
        int idCategorie = categoryRepository.getIdByLibelle(product.getCategorie());
        try {
            String query = "INSERT INTO produits (libelle, quantite, prixUnitaire, idcategorie) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, product.getLibelle());
            preparedStatement.setInt(2, product.getQuantite());
            preparedStatement.setDouble(3, product.getPrixUnitaire());
            preparedStatement.setInt(4, idCategorie);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        int idCategorie = categoryRepository.getIdByLibelle(product.getCategorie());
        try {
            String query = "UPDATE produits SET libelle = ?, quantite = ?, prixUnitaire = ?, idcategorie = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, product.getLibelle());
            preparedStatement.setInt(2, product.getQuantite());
            preparedStatement.setDouble(3, product.getPrixUnitaire());
            preparedStatement.setInt(4, idCategorie);
            preparedStatement.setInt(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        try {
            String query = "DELETE FROM produits WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Product> getAll() {
        ObservableList<Product> produits = null;
try {
            produits = FXCollections.observableArrayList();
            String query = "SELECT * FROM produits";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setLibelle(resultSet.getString("libelle"));
                product.setQuantite(resultSet.getInt("quantite"));
                product.setPrixUnitaire(resultSet.getDouble("prixUnitaire"));
                product.setCategorie(categoryRepository.getLibelleById(resultSet.getInt("idcategorie")));
                produits.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return produits;
    }

    // nombre de produit par catégorie et le mettre dans un pie chart
    public ObservableList<Product> getNombreProduitParCategorie() {
        ObservableList<Product> produits = null;
        try {
            produits = FXCollections.observableArrayList();
            String query = "SELECT c.libelle, COUNT(p.id) as nombreProduit FROM produits p, categories c WHERE p.idcategorie = c.id GROUP BY c.libelle";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setCategorie(resultSet.getString("libelle"));
                product.setQuantite(resultSet.getInt("nombreProduit"));
                produits.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return produits;
    }
}
