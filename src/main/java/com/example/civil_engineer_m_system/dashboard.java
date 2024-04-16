package com.example.civil_engineer_m_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dashboard {

    @FXML
    private AnchorPane ap;
    @FXML
    private BorderPane bp;
    @FXML
    private Label dashName;

    @FXML
    private TableView<user> tableView;

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
    public void initializeData(String username) {
            dashName.setText("Welcome, " + username + "!");
        // Add any other initialization logic for the dashboard
    }




    @FXML
    void button1(MouseEvent event) {
        bp.setCenter(ap);
    }
    @FXML
    void button2(MouseEvent event) throws IOException {
        loadpage("addTable");
    }
    @FXML
    void button3(MouseEvent event) throws IOException {
        loadpage("todoList");
    }


    private void loadpage(String page) throws IOException {
        Parent root = null;

        try{
            root = FXMLLoader.load(this.getClass().getResource(page+".fxml"));

        }catch (IOException ex){
            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE,null,ex);
        }
        bp.setCenter(root);
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
