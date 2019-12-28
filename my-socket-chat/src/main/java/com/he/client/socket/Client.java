package com.he.client.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    private Socket         clientSocket;
    private PrintWriter    out;
    private BufferedReader in;
    private boolean doListenMessages;

    private List<ClientMessageListener> clientMessageListeners;

    public void addClientMessageListener(ClientMessageListener listener) {
        this.clientMessageListeners.add(listener);
    }

    public Client() {
        this.clientMessageListeners = new CopyOnWriteArrayList<>();
    }

    public void addMessageListener(ClientMessageListener listener) {
        this.clientMessageListeners.add(listener);
    }

    public void startConnection(String ip, int port) {
        try {
            this.clientSocket = new Socket(ip, port);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.doListenMessages = true;
            new Thread(receiverMessagesTask).start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private Runnable receiverMessagesTask = new Runnable() {
        @Override
        public void run() {
            System.out.println("i ma here");
            while (doListenMessages) {
                try {
                    String message = in.readLine();
                    if (message != null) {
                        clientMessageListeners.forEach(x -> x.onMessage(message));
                    } else {
                        doListenMessages = false;
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
            System.out.println("i am out");
        }
    };

    public void stopConnection() {
        try {
            this.doListenMessages = false;
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
