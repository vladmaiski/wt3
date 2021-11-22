package by.bsuir.wt.lab.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static by.bsuir.wt.lab.core.ServerConstants.SERVER_PORT;

public class ServerController extends Thread {

    private static final int BACKLOG = 50;

    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT, BACKLOG, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server is running");

        while (true) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                var client = new ServerClientController(clientSocket, this);
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect(ServerClientController client) {
        try {
            client.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client disconnected");
    }

}
