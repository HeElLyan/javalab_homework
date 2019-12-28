package com.he.presenters;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PresenterImpl implements Presenter {
    private Template            template;
    private Map<String, Object> root;

    public PresenterImpl(Template template) {
        this.root = new HashMap<>();
        this.template = template;
    }

    @Override
    public void put(String key, Object value) {
        this.root.put(key, value);
    }

    @Override
    public void render(PrintWriter out) throws IOException {
        try {
            this.template.process(root, out);
            out.close();
        } catch (TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

}
