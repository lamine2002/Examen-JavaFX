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
    public ProductRepository() {
        this.conn = new BD().getConnection();
    }

    public void addProduct(Product product) {
        try {
            String query = "INSERT INTO products (libelle, quantite, prix_unitaire, categorie) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, product.getLibelle());
            preparedStatement.setInt(2, product.getQuantite());
            preparedStatement.setDouble(3, product.getPrixUnitaire());
            preparedStatement.setString(4, product.getCategorie());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try {
            String query = "UPDATE products SET libelle = ?, quantite = ?, prix_unitaire = ?, categorie = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, product.getLibelle());
            preparedStatement.setInt(2, product.getQuantite());
            preparedStatement.setDouble(3, product.getPrixUnitaire());
            preparedStatement.setString(4, product.getCategorie());
            preparedStatement.setInt(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        try {
            String query = "DELETE FROM products WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Product> getAll() {
        ObservableList<Product> products = null;
try {
            products = FXCollections.observableArrayList();
            String query = "SELECT * FROM products";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setLibelle(resultSet.getString("libelle"));
                product.setQuantite(resultSet.getInt("quantite"));
                product.setPrixUnitaire(resultSet.getDouble("prix_unitaire"));
                product.setCategorie(resultSet.getString("categorie"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
