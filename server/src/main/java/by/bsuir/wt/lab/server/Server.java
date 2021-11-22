package by.bsuir.wt.lab.server;


import by.bsuir.wt.lab.server.controller.ServerController;

public class Server {

    public static void main(String[] args) {
        ServerController server = new ServerController();
        server.start();
    }

}
