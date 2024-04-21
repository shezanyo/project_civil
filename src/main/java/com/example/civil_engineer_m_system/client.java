package com.example.civil_engineer_m_system;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageTextField;

    private PrintWriter out;
    private BufferedReader in;

    public void initialize() {
        new Thread(this::connectToServer).start();
    }

    void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to continuously listen for messages from the server
            new Thread(this::receiveMessages).start();

            System.out.println("Connected to server."); // Log connection status to terminal
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to server: " + e.getMessage()); // Log connection failure to terminal
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> chatTextArea.appendText(finalMessage + "\n"));
                System.out.println("Received: " + message); // Log received messages to terminal
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error receiving message: " + e.getMessage()); // Log receiving error to terminal
        }
    }

    @FXML
    void sendMessage() {
        String message = messageTextField.getText();
        out.println(message);
        messageTextField.clear();
        System.out.println("Sent: " + message); // Log sent messages to terminal
    }

    // Close resources when the application is closed
    public void close() {
        try {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
