package com.he.server.protocol;

public interface RequestDispatcher<T extends Request, O extends Response> {

    void doDispatch(T req, O resp) throws Exception;

}
