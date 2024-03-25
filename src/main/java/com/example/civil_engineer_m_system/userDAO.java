package com.example.civil_engineer_m_system;

import com.example.civil_engineer_m_system.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
    public static boolean authenticateUser(String username, String password) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM new_table WHERE id=? AND pass=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // If user exists, authentication successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
