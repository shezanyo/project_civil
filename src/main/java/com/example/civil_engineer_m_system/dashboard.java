package com.example.civil_engineer_m_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
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

    private int uId;

    public void setUId(int uId) {
        this.uId=uId;

    }

    public void initialize() {
        loadDashName();
    }

    private void loadDashName(){

        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();

        String name="SELECT lastname FROM new_table WHERE id =" +uId;
        System.out.println(uId);
        System.out.println(name);

        try {
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(name);
            while(queryResult.next()){
                dashName.setText("Welcome "+queryResult.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void button1(MouseEvent event) {
        bp.setCenter(ap);
    }
    @FXML
    void button2(MouseEvent event) throws IOException {
        loadpage("test");
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
}
