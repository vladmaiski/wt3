package by.bsuir.wt.lab.server.controller;

import by.bsuir.wt.lab.server.command.CommandFactory;
import by.bsuir.wt.lab.server.command.exception.CommandException;
import by.bsuir.wt.lab.server.command.impl.DisconnectCommand;
import by.bsuir.wt.lab.server.controller.filter.Filter;
import by.bsuir.wt.lab.server.controller.filter.impl.AccessFilter;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

import static by.bsuir.wt.lab.server.controller.constant.ControllerConstants.AVAILABLE_COMMANDS;

public class ServerClientController extends Thread {

    private final Socket socket;
    private final ServerController serverController;

    private BufferedReader reader;
    private PrintWriter writer;

    private final List<Filter> filters = List.of(new AccessFilter());

    public ServerClientController(Socket socket, ServerController serverController) {
        this.socket = socket;
        this.serverController = serverController;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendMessage(AVAILABLE_COMMANDS);

        var running = true;
        do {
            try {
                var request = readMessage();
                if (request == null) {
                    break;
                }
                for (Filter filter : filters) {
                    filter.doFilter(request, this);
                }

                var command = CommandFactory.getInstance().createCommand(request);
                var response = command.execute(this, request);
                sendMessage(response.message());

                if (command instanceof DisconnectCommand) {
                    running = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(e.getMessage());
            }
        } while (running);

        serverController.disconnect(this);
    }

    private String readMessage() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerClientController that = (ServerClientController) o;
        return socket.equals(that.socket) && serverController.equals(that.serverController);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, serverController);
    }

}
