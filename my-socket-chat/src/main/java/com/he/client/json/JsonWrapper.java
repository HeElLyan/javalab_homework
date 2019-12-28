package com.he.client.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonWrapper {

    private ObjectMapper    mapper;
    private JsonNodeFactory factory;

    public JsonWrapper() {
        this.mapper = new ObjectMapper();
        this.factory = mapper.getNodeFactory();
    }

    public String getSignIn(String login, String password) {
        ObjectNode payload = factory.objectNode();
        payload.set("login", factory.textNode(login));
        payload.set("password", factory.textNode(password));
        ObjectNode json = factory.objectNode();
        json.set("header", factory.textNode("Sign in"));
        json.set("payload", payload);
        return json.toString();
    }

    public String getAllProducts(String token) {
        ObjectNode payload = factory.objectNode();
        payload.set("token", factory.textNode(token));
        ObjectNode json = factory.objectNode();
        json.set("header", factory.textNode("Get all products"));
        json.set("payload", payload);
        return json.toString();
    }

    public String getAddProduct(String name, String token) {
        ObjectNode payload = factory.objectNode();
        payload.set("token", factory.textNode(token));
        payload.set("name", factory.textNode(name));
        ObjectNode json = factory.objectNode();
        json.set("header", factory.textNode("Add product"));
        json.set("payload", payload);
        return json.toString();
    }

    public String getBuyProduct(int id, String token) {
        ObjectNode payload = factory.objectNode();
        payload.set("token", factory.textNode(token));
        payload.set("id", factory.textNode(String.valueOf(id)));
        ObjectNode json = factory.objectNode();
        json.set("header", factory.textNode("Buy product"));
        json.set("payload", payload);
        return json.toString();
    }

    public String getGetUserProducts(String token) {
        ObjectNode payload = factory.objectNode();
        payload.set("token", factory.textNode(token));
        ObjectNode json = factory.objectNode();
        json.set("header", factory.textNode("Get user products"));
        json.set("payload", payload);
        return json.toString();
    }
}
