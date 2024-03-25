package com.example.civil_engineer_m_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientHandler extends Thread {
    private final Socket clientSocket;

    public clientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Receive authentication request from the client
            String request = in.readLine();
            if ("AUTHENTICATE".equals(request)) {
                // Assuming you have access to UserDAO class
                String username = in.readLine();
                String password = in.readLine();

                boolean isAuthenticated = userDAO.authenticateUser(username, password);

                // Send authentication result back to the client
                if (isAuthenticated) {
                    out.println("AUTH_SUCCESS");
                } else {
                    out.println("AUTH_FAIL");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
