package com.example.civil_engineer_m_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
         private static final String databaseName="scematest";
         private static final String databaseUser="root";
        private static final String databasePassword="1234";
        private static final String url="jdbc:mysql://localhost:3306/"+databaseName;

         public static Connection getConnection() throws SQLException {
             return DriverManager.getConnection(url, databaseUser, databasePassword);
         }
}
