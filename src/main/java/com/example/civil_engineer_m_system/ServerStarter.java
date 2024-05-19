package com.example.civil_engineer_m_system;

import java.io.IOException;

public class ServerStarter {
    public static void main(String[] args) {
        // Define the port number on which the server will listen
        int port = 12345; // You can change this to any available port number

        // Create an instance of the server
        server serverInstance = new server();

        try {
            // Start the server
            serverInstance.start(port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
