package com.example.civil_engineer_m_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class login {

    @FXML
    private TextField userId,passId;
    @FXML
    private Label loginInfo;
    private int uId;
    Stage stage;
    Parent root;

    public void loginButtonAction(ActionEvent e){
        if(!userId.getText().isBlank() && !passId.getText().isBlank()){
            databaseConnection connectNow = new databaseConnection();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin ="SELECT count(1) FROM new_table WHERE id= " +userId.getText()+ " AND pass ='" +passId.getText()+ "'";

            try{
                Statement statement=connectDB.createStatement();
                ResultSet queryResult=statement.executeQuery(verifyLogin);

                while(queryResult.next()){
                    if(queryResult.getInt(1)==1){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                        Parent root = loader.load();
                        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        dashboard controller = loader.getController();
                        controller.initializeData(userId.getText()); // Set dashboard data


                        Scene scene = new Scene(root);
                        stage.setTitle("Dash");
                        stage.setScene(scene);
                        stage.show();

                    }
                    else{
                        loginInfo.setText("wrong cred");
                    }

                }
            } catch (Exception ex){
                ex.printStackTrace();
                System.err.println("Error starting the server: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else{
            loginInfo.setText("fail");
        }
    }


    public  void signUpButton(ActionEvent e) throws IOException {
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("signup.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Dash");
        stage.setScene(scene);
        stage.show();
    }

}
