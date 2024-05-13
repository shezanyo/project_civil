package com.example.civil_engineer_m_system;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class client {

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageTextField;

    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public void initData(String username) {
        this.username = username;
        connectToServer(); // initiate connection to server
    }

    /*public void initialize() {
        new Thread(this::connectToServer).start();
    }*/

    void connectToServer() {
        try {
            Socket socket = new Socket("192.168.0.8", 12345);
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

    /*@FXML
    private void sendMessage() {
        String message = username + ": " + messageTextField.getText();
        out.println(message);
        messageTextField.clear();
        System.out.println("Sent: " + message); // Log sent messages to terminal
    }*/

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
    @FXML
    private void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) chatTextArea.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final String CHAT_HISTORY_FILE = "chat_history.txt";

    // Method to save the chat history to a file
    private void saveChatHistory(String message) {
        try (FileWriter writer = new FileWriter(CHAT_HISTORY_FILE, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving chat history: " + e.getMessage());
        }
    }

    // Method to load the chat history from a file
    private List<String> loadChatHistory() {
        List<String> chatHistory = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CHAT_HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                chatHistory.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading chat history: " + e.getMessage());
        }
        return chatHistory;
    }

    // Method to initialize the chat history when the application starts
    public void initialize() {
        // Load chat history
        List<String> chatHistory = loadChatHistory();
        // Display chat history in the TextArea
        chatHistory.forEach(message -> chatTextArea.appendText(message + "\n"));
        // Connect to server
        new Thread(this::connectToServer).start();
    }

    // Method to handle sending messages
    @FXML
    private void sendMessage() {
        String message = username + ": " + messageTextField.getText();
        out.println(message);
        saveChatHistory(message); // Save the message to the chat history file
        messageTextField.clear();
        System.out.println("Sent: " + message);
    }
}
