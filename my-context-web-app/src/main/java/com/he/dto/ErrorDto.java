package com.he.dto;

public class ErrorDto implements Dto {
    private String errorMessage;

    public ErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
