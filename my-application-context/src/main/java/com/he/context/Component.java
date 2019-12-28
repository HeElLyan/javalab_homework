package com.he.context;

public interface Component {

    String getName();

    default void saveContext(ApplicationContext context){
    }

}
