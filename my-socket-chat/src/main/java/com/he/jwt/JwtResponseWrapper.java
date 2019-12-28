package com.he.jwt;

import com.he.server.dto.Dto;

public class JwtResponseWrapper<D extends Dto> implements JwtResponse {

    private String header;
    private D      payload;
    private int    error;

    public JwtResponseWrapper(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public D getPayload() {
        return payload;
    }

    public int getErrorCode() {
        return error;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public <T extends Dto> void setData(T data) {
        this.payload = (D) data;
    }

    public void setErrorCode(int error) {
        this.error = error;
    }

}
