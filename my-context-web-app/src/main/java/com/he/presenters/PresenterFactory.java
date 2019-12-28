package com.he.presenters;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PresenterFactory {

    private Configuration       configuration;
    private Map<String, String> templates;

    public PresenterFactory(ServletContext servletContext) {
        this.configuration = new Configuration(Configuration.VERSION_2_3_29);
        this.configuration.setServletContextForTemplateLoading(servletContext, "");
        this.configuration.setEncoding(new Locale("RU", "ru"), "UTF-8");
        this.templates = new HashMap<>();
    }

    public void configure(String propertiesPath) {
        File properties = new File(propertiesPath);
        System.out.println("property file is: " + properties);
        try {
            Files.lines(properties.toPath())
                    .map(l -> l.split(" = "))
                    .forEach(sa -> templates.put(sa[0], sa[1]));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public Presenter createPresenter(String name) throws IOException {
        String templatePath = this.templates.get(name);
        Template template = this.configuration.getTemplate(templatePath);
        return new PresenterImpl(template);
    }

}
