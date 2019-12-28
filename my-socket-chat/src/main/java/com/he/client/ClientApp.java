package com.he.client;

import com.he.client.socket.Client;
import com.he.client.socket.ClientMessageListener;
import com.he.client.json.JsonWrapper;

import java.util.Scanner;

public class ClientApp implements Runnable, ClientMessageListener {

    public static void main(String[] args) {
        new ClientApp().run();
    }

    @Override
    public void run() {
        Client client = new Client();
        client.addMessageListener(this);
        client.startConnection("localhost", 7320);

        JsonWrapper wrapper = new JsonWrapper();
        Scanner scanner = new Scanner(System.in);
        String[] input;
        String command;
        String[] args;
        String json;

        while (true) {
            try {
                input = scanner.nextLine().split(" ", 2);
                command = input[0];
                switch (command) {
                    case "/sign_in":
                        args = input[1].split(" ");
                        String login = args[0];
                        String password = args[1];
                        json = wrapper.getSignIn(login, password);
                        break;
                    case "/get_products":
                        args = input[1].split(" ");
                        String token = args[0];
                        json = wrapper.getAllProducts(token);
                        break;
                    case "/add_product":
                        args = input[1].split(" ");
                        token = args[0];
                        String name = args[1];
                        json = wrapper.getAddProduct(name, token);
                        break;
                    case "/buy_product":
                        args = input[1].split(" ");
                        token = args[0];
                        int id = Integer.parseInt(args[1]);
                        json = wrapper.getBuyProduct(id, token);
                        break;
                    case "/get_my_products":
                        args = input[1].split(" ");
                        token = args[0];
                        json = wrapper.getGetUserProducts(token);
                        break;
                    case "/stop":
                        return;
                    default:
                        throw new IllegalArgumentException("unrecognizable");
                }
                client.sendMessage(json);
            } catch (Exception e) {
                System.out.println("invalid command");
            }

        }

    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }
}
