package by.bsuir.wt.lab.client;

import by.bsuir.wt.lab.client.controller.ClientController;

public class Client {

    public static void main(String[] args) {
        var client = new ClientController();
        client.start();
    }

}
