package com.he.server.protocol;

public interface RequestsHandler<I extends Request, O extends Response> {

    O handleRequest(I request);

}
