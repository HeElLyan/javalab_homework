package com.he.jwt.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenCoderAuth0Based extends JwtTokenCoder {

    public JwtTokenCoderAuth0Based(String secret) {
        super(secret);
    }

    @Override
    public String encode(String sub, String role) {
        return JWT.create()
                .withSubject(sub)
                .withClaim("rol", role)
                .sign(Algorithm.HMAC256(super.secret));
    }

    @Override
    public DecodedJwtToken decode(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt = verifier.verify(token);
        return new DecodedJwtToken() {
            @Override
            public String getSubject() {
                return jwt.getSubject();
            }

            @Override
            public String getRole() {
                return jwt.getClaim("rol").asString();
            }

            @Override
            public String getType() {
                return jwt.getType();
            }

            @Override
            public String getAlgorithm() {
                return jwt.getAlgorithm();
            }

            @Override
            public String toString() {
                return "DecodedJwtToken{" +
                        "sub='" + getSubject() + '\'' +
                        ", rol='" + getRole() + '\'' +
                        ", typ='" + getType() + '\'' +
                        ", alg='" + getAlgorithm() + '\'' +
                        '}';
            }
        };
    }

}
