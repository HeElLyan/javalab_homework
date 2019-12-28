package com.he.jwt.token;

public interface DecodedJwtToken {
    String getSubject();
    String getRole();
    String getType();
    String getAlgorithm();
}
