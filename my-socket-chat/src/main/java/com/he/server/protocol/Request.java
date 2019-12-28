package com.he.server.protocol;

public interface Request {

    String getCommand();

    String getParameter(String name);

}
