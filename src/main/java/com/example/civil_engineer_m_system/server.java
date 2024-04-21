package com.example.civil_engineer_m_system;

import java.io.*;
import java.net.*;
import java.util.*;

public class server {
    private ServerSocket serverSocket;
    private final List<PrintWriter> clientOutputStreams = new ArrayList<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            clientOutputStreams.add(out);

            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String message;
                    while ((message = in.readLine()) != null) {
                        broadcast(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void broadcast(String message) {
        for (PrintWriter out : clientOutputStreams) {
            out.println(message);
        }
    }
}
