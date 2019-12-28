package com.he.context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ApplicationContextReflectionBased implements ApplicationContext {

    private Map<String, Component> components;
    private Map<String, Object> attributes;

    public ApplicationContextReflectionBased() {
        this.attributes = new HashMap<>();
        this.components = new HashMap<>();
        MyReflections reflections = new MyReflections();
        System.out.println(reflections.getSubTypesOf(Component.class));
        Set<Class<? extends Component>> allComponents = reflections.getSubTypesOf(Component.class);
        for (Class<? extends Component> componentClass : allComponents) {
            if (!componentClass.isInterface())
                try {
                    Component component = componentClass.getConstructor().newInstance();
                    components.put(component.getName(), component);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
        }
        List<Component> objectComponents = new ArrayList<>(components.values());
        for (Component objComponent1 : objectComponents) {
            for (Field field : objComponent1.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                for (Component objComponent2 : objectComponents) {
                    if (field.getType().isAssignableFrom(objComponent2.getClass())) {
                        try {
                            field.set(objComponent1, objComponent2);
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                }
            }
        }
        this.components.values().forEach(x -> x.saveContext(this));
    }

    @Override
    public <T> T getComponent(Class<T> componentType, String name) {
        return componentType.cast(components.get(name));
    }

    @Override
    public <T> T getAttribute(String key) {
        return (T)this.attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    @Override
    public String toString() {
        return "ApplicationContextReflectionBased{" +
                "components=" + components +
                '}';
    }
}
