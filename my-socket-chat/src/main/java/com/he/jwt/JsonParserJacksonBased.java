package com.he.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.he.jwt.token.DecodedJwtToken;
import com.he.jwt.token.JwtTokenCoder;
import com.he.server.protocol.StringParser;

import java.io.IOException;

public class JsonParserJacksonBased implements StringParser<JwtRequest, JwtResponse> {

    private JwtTokenCoder tokenCoder;

    public JsonParserJacksonBased(JwtTokenCoder tokenCoder) {
        this.tokenCoder = tokenCoder;
    }

    @Override
    public String toJson(JwtResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public JwtRequest buildRequest(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode tempNode = mapper.readValue(json, ObjectNode.class);
            TextNode header = ((TextNode) tempNode.get("header"));
            ObjectNode payload = ((ObjectNode) tempNode.get("payload"));
            return buildRequest(header, payload);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private JwtRequest buildRequest(TextNode textNode, ObjectNode objectNode) {
        return new JwtRequest() {

            @Override
            public String getCommand() {
                return textNode.asText();
            }

            @Override
            public String getParameter(String name) {
                try {
                    return objectNode.get(name).asText();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public DecodedJwtToken getDecodedToken() {
                try {
                    return tokenCoder.decode(objectNode.get("token").asText());
                } catch (Exception e){
                    return null;
                }
            }
        };
    }

}
