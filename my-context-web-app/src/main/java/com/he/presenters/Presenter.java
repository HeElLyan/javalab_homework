package com.he.presenters;

import java.io.IOException;
import java.io.PrintWriter;

public interface Presenter {

    void put(String key, Object value);

    void render(PrintWriter out) throws IOException;

}
