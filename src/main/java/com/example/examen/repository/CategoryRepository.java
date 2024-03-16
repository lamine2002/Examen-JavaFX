package com.example.examen.repository;

import com.example.examen.BD.BD;
import com.example.examen.lib.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CategoryRepository {
    private Connection conn;
    public CategoryRepository() {
        this.conn = new BD().getConnection();
    }

    public ObservableList<Category> getAll() {
        ObservableList<Category> categories = null;
        try {
            categories = FXCollections.observableArrayList();
            String query = "SELECT * FROM categories";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setLibelle(resultSet.getString("libelle"));
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void addCategory(Category category) {
        try {
            String query = "INSERT INTO categories (libelle) VALUES (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, category.getLibelle());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        try {
            String query = "UPDATE categories SET libelle = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, category.getLibelle());
            preparedStatement.setInt(2, category.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int id) {
        try {
            String query = "DELETE FROM categories WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
