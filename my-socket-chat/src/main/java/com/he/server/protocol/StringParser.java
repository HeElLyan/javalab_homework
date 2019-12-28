package com.he.server.protocol;

public interface StringParser<I extends Request, O extends Response> {

    String toJson(O response);

    I buildRequest(String json);

}
