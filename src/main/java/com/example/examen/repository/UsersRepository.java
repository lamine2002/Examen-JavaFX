package com.example.examen.repository;

import com.example.examen.BD.BD;
import com.example.examen.lib.Users;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsersRepository {
    private Connection conn;

    public UsersRepository() {
        this.conn = new BD().getConnection();
    }

    public void addUser(Users user) {
        try {
            String query = "INSERT INTO utilisateurs (nom, prenom, telephone, email, login, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getTelephone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Users user) {
        try {
            String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, telephone = ?, email = ?, login = ?, password = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getTelephone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setInt(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        try {
            String query = "DELETE FROM utilisateurs WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Users> getAllUsers() {
        return null;
    }
}
