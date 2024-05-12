package com.example.civil_engineer_m_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<user, Double> quantitycol;

    @FXML
    private TextField quantityent;
    @FXML
    private Button deleteButton;

    private databaseConnection dbConnection;
    private ObservableList<user> productList;

    @FXML
    public void initialize() {
        dbConnection = new databaseConnection();
        dbConnection.getConnection(); // Establishing database connection
        productList = FXCollections.observableArrayList();
        loadTableData();
        tableView.setItems(productList);
        itemcol.setCellValueFactory(new PropertyValueFactory<user, String>("item"));
        pricecol.setCellValueFactory(new PropertyValueFactory<user, Double>("price"));
        quantitycol.setCellValueFactory(new PropertyValueFactory<user, Double>("quantity"));
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Enable delete button when a row is selected
                deleteButton.setDisable(false);
            } else {
                // Disable delete button when no row is selected
                deleteButton.setDisable(true);
            }
        });
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
    @FXML
    private void deleteSelectedItem() {
        // Get the selected item from the table
        user selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Display a confirmation dialog before deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this item?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Delete the item from the database
                        Connection connection = dbConnection.getConnection();
                        PreparedStatement statement = connection.prepareStatement("DELETE FROM new_table2 WHERE item = ?");
                        statement.setString(1, selectedItem.getItem());
                        statement.executeUpdate();

                        // Remove the item from the TableView
                        tableView.getItems().remove(selectedItem);

                        // Optionally, display a success message
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Item deleted successfully.");
                        successAlert.showAndWait();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void loadTableData() {
        productList.clear();
        try {
            Connection connection = dbConnection.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM new_table2");

            while (resultSet.next()) {
                productList.add(new user(resultSet.getString("item"), resultSet.getDouble("quantity"), resultSet.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
