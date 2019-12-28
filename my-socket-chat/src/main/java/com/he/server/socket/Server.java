package com.he.server.socket;

import com.he.server.protocol.Request;
import com.he.server.protocol.RequestsHandler;
import com.he.server.protocol.Response;
import com.he.server.protocol.StringParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    private ServerSocket serverSocket;

    private List<ClientHandler> clients;

    private RequestsHandler<Request, Response> requestsHandler;

    private StringParser<Request, Response> stringParser;

    public Server(
            RequestsHandler<? extends Request, ? extends Response> requestsHandler,
            StringParser<? extends Request, ? extends Response> stringParser) {
        this.stringParser = (StringParser<Request, Response>) stringParser;
        this.requestsHandler = (RequestsHandler<Request, Response>) requestsHandler;
        this.clients = new CopyOnWriteArrayList<>();
    }

    public void start(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        while (true) {
            try {
                new ClientHandler(this.serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader fromClient;
        private PrintWriter toClient;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            clients.add(this);
        }

        @Override
        public void run() {
            try {
                this.fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.toClient = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = fromClient.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        break;
                    } else {
                        Request request = stringParser.buildRequest(inputLine);
                        Response response = requestsHandler.handleRequest(request);
                        this.toClient.println(stringParser.toJson(response));
                    }
                }

                fromClient.close();
                clientSocket.close();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
