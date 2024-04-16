package com.example.civil_engineer_m_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class databaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName="scematest";
        String databaseUser="root";
        String databasePassword="1234";
        String url="jdbc:mysql://localhost:3306/"+databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink= DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }


}
