package com.he.context;

public interface ApplicationContext {

    <T> T getComponent(Class<T> componentType, String name);

    <T> T getAttribute(String key);

    void setAttribute(String key, Object value);
}
