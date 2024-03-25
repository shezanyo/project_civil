package com.example.civil_engineer_m_system;// Client.java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Assuming you have username and password
            String username = "sampleUser";
            String password = "samplePassword";

            // Send authentication request to the server
            out.println("AUTHENTICATE");
            out.println(username);
            out.println(password);

            // Receive authentication result from the server
            String response = in.readLine();
            if ("AUTH_SUCCESS".equals(response)) {
                System.out.println("Authentication successful. You can now access the dashboard.");
                // Add code to navigate to the dashboard
            } else {
                System.out.println("Authentication failed. Please check your credentials.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
