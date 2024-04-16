package com.example.civil_engineer_m_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class addTable {

    @FXML
    private TableView<user> tableView;

    @FXML
    private TableColumn<user, String> itemcol;

    @FXML
    private TextField itement;

    @FXML
    private TableColumn<user, Double> pricecol;

    @FXML
    private TextField priceent;

    @FXML
    private TableColumn<user, Integer> quantitycol;

    @FXML
    private TextField quantityent;

    private databaseConnection dbConnection;
    private ObservableList<user> productList;

    @FXML
    public void initialize() {
        dbConnection = new databaseConnection();
        dbConnection.getConnection(); // Establishing database connection
        productList = FXCollections.observableArrayList();
        loadTableData();
        tableView.setItems(productList);
    }

    @FXML
    private void addItemToDatabase() {
        String item = itement.getText();
        double price = Double.parseDouble(priceent.getText());
        int quantity = Integer.parseInt(quantityent.getText());

        // SQL statement to insert data into the database
        String sql = "INSERT INTO new_table2 (item, price, quantity) VALUES (?, ?, ?)";

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, item);
            statement.setDouble(2, price);
            statement.setInt(3, quantity);

            // Execute the SQL statement
            statement.executeUpdate();

            // Reload table data
            loadTableData();

            // Optionally, you can clear the text fields after successful insertion
            itement.clear();
            priceent.clear();
            quantityent.clear();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        productList.clear();
        try {
            Connection connection = dbConnection.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM new_table2");

            while (resultSet.next()) {
                productList.add(new user(resultSet.getString("item"), resultSet.getDouble("price"), resultSet.getInt("quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
