package com.example.civil_engineer_m_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dashboard {

    @FXML
    private AnchorPane ap;
    @FXML
    private BorderPane bp;
    @FXML
    private Label dashName;
    Stage stage;
    Parent root;
    private int uIdProfile;

    @FXML
    private TableView<user> tableView;
    @FXML
    private TableColumn<user, String> itemcol;
    @FXML
    private TableColumn<user, Double> pricecol;
    @FXML
    private TableColumn<user, Double> quantitycol;

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
    }

    public void initializeData(String username) {

        dashName.setText("Welcome, " + username + "!");
        uIdProfile = Integer.parseInt(username);
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

        try {
            root = FXMLLoader.load(this.getClass().getResource(page + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        bp.setCenter(root);
    }

    private void loadTableData() {
        productList.clear();
        try {
            Connection connection = dbConnection.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM new_table2");

            while (resultSet.next()) {
                productList.add(new user(resultSet.getString("item"), resultSet.getDouble("price"), resultSet.getDouble("quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buttonProfile(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        profile passId = loader.getController();
        passId.setUniqueId(uIdProfile);
        passId.loadData(); // Load profile data
        Scene scene = new Scene(root);
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }
    public void chatButton(ActionEvent e) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        //profile passId = loader.getController();
        //passId.setUniqueId(uIdProfile);
        //passId.loadData(); // Load profile data
        Scene scene = new Scene(root);
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.show();
    }


}
