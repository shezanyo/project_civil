package com.example.civil_engineer_m_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profile {
    @FXML
    private Label firstName;

    @FXML
    private Label id;

    @FXML
    private Label lastName;

    private int uniqueId;

    @FXML
    public void initialize() throws SQLException {
        System.out.println("Initializing profile controller...");
        loadData();
        System.out.println(uniqueId);
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
        System.out.println("Unique ID set: " + uniqueId); // Add this line to verify if the uniqueId is correctly set
    }


    public void loadData() throws SQLException {
        System.out.println("Loading data for ID: " + uniqueId);

        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT id, firstname, lastname FROM new_table WHERE id = ?;";

        PreparedStatement statement = connectDB.prepareStatement(query);
        statement.setInt(1, uniqueId);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            id.setText(String.valueOf(resultSet.getInt("id")));
            firstName.setText(resultSet.getString("firstname"));
            lastName.setText(resultSet.getString("lastname"));

            System.out.println("Data loaded successfully!");
        } else {
            System.out.println("No data found for ID: " + uniqueId);
        }
    }
    @FXML
    private void goBackToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) firstName.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
