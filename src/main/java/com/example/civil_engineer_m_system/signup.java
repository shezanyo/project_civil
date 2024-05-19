package com.example.civil_engineer_m_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class signup {

    @FXML
    private TextField engIdTextfield,firstNameTextfield,lastNameTextfield,passwordTextfield;

    @FXML
    private Label errorMsg;

    Stage stage;
    Parent root;

    public void signupButtonAction(ActionEvent e){

            databaseConnection connectNow = new databaseConnection();
            Connection connectDB = connectNow.getConnection();

            String engId=engIdTextfield.getText();
            String firstName=firstNameTextfield.getText();
            String lastName=lastNameTextfield.getText();
            String password=passwordTextfield.getText();



            String insertField ="INSERT INTO new_table (id,firstname,lastname,pass) VALUES (";
            String insertValues=engId + ",'" + firstName + "','" + lastName + "','" + password + "')";
            String insertTogether=insertField+insertValues;

            try{
                Statement statement=connectDB.createStatement();
                statement.executeUpdate(insertTogether);
                errorMsg.setText("Registered!");
            }catch (Exception ex){
                ex.printStackTrace();
            }

    }
    public void backButton(ActionEvent e) throws IOException {
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Dash");
        stage.setScene(scene);
        stage.show();
    }

}
