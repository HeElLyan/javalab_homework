package com.he.jwt;

import com.he.server.dto.Dto;
import com.he.server.dto.ErrorDto;
import com.he.server.protocol.RequestDispatcher;
import com.he.server.protocol.RequestsHandler;

public class JwtRequestsHandler implements RequestsHandler<JwtRequest, JwtResponse> {

    private RequestDispatcher<JwtRequest, JwtResponse> dispatcher;

    public JwtRequestsHandler(JwtRequestsDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public JwtResponse handleRequest(JwtRequest request) {
        String header = request.getCommand();
        JwtResponseWrapper<Dto> response = new JwtResponseWrapper<>(header);
        response.setHeader(header);
        try {
            this.dispatcher.doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setErrorCode(1);
            response.setData(new ErrorDto(e.getMessage()));
        }
        return response;
    }

}
