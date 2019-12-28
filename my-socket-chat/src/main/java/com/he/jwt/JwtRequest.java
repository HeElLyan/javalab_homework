package com.he.jwt;

import com.he.jwt.token.DecodedJwtToken;
import com.he.server.protocol.Request;

public interface JwtRequest extends Request {

    DecodedJwtToken getDecodedToken();

}
