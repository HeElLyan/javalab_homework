package com.he.server;

import com.he.jwt.*;
import com.he.server.protocol.RequestsHandler;
import com.he.server.socket.Server;
import com.he.context.ApplicationContext;
import com.he.context.ApplicationContextReflectionBased;
import com.he.jwt.token.JwtTokenCoderAuth0Based;
import com.he.server.services.ProductService;
import com.he.server.services.SignInService;

public class ServerApp implements Runnable {

    public static void main(String[] args) {
        new ServerApp().run();
    }

    @Override
    public void run() {
        String tokenSecret = "metal";
        ApplicationContext context = new ApplicationContextReflectionBased();
        SignInService signInService = context.getComponent(SignInService.class, "signInService");
        ProductService productService = context.getComponent(ProductService.class, "productService");
        RequestsHandler<JwtRequest, JwtResponse> requestsHandler
                = new JwtRequestsHandler(new JwtRequestsDispatcher(productService, signInService));
        Server server = new Server(requestsHandler, new JsonParserJacksonBased(new JwtTokenCoderAuth0Based(tokenSecret)));
        server.start(7229);
    }
}
