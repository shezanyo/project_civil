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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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

    public void loginButtonAction(ActionEvent e) {
        if (userId.getText().isBlank() == false && passId.getText().isBlank() == false) {
            String username = userId.getText();
            String password = passId.getText();

            // Communicate with the server to validate credentials
            boolean authenticated = communicateWithServer(username, password);

            if (authenticated) {
                // Load the dashboard FXML file
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Parent root = loader.load();

                    // Access the controller of the dashboard if you need to pass data
                    dashboard dashboardController = loader.getController();
                    dashboardController.initializeData(username); // Example: Passing the username

                    Stage stage = (Stage) userId.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Handle the exception (e.g., show an error dialog)
                }
            } else {
                // Show error message
                System.out.println("Authentication failed. Please check your credentials.");
            }
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

    private boolean communicateWithServer(String username, String password) {
        // Replace this with your actual communication logic
        // For example, you might use sockets to communicate with the server.
        // Here, we're using the Client class as an example.

        boolean authenticated = false;
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send authentication request to the server
            out.println("AUTHENTICATE");
            out.println(username);
            out.println(password);

            // Receive authentication result from the server
            String response = in.readLine();
            authenticated = "AUTH_SUCCESS".equals(response);

        } catch (IOException e) {
            e.printStackTrace();
            // Consider handling this more gracefully in a real application
        }

        return authenticated;
    }

}
