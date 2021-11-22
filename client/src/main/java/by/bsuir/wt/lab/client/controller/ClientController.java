package by.bsuir.wt.lab.client.controller;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static by.bsuir.wt.lab.core.ServerConstants.SERVER_PORT;

public class ClientController extends Thread {

    private PrintWriter writer;
    private boolean running = true;

    @Override
    public void run() {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), SERVER_PORT);
            ResponseController input = new ResponseController(this);
            input.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    public boolean isRunning() {
        return running;
    }

}
