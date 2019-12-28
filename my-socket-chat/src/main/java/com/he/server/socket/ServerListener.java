package com.he.server.socket;

import com.he.server.protocol.Request;
import com.he.server.protocol.Response;

public interface ServerListener {

    void onMessage(Request request, Response response);

}
