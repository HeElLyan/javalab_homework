package com.he.jwt.token;

public abstract class JwtTokenCoder {

    protected String secret;

    public JwtTokenCoder(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return this.secret;
    }

    public abstract String encode(String sub, String role);

    public abstract DecodedJwtToken decode(String token);

}
