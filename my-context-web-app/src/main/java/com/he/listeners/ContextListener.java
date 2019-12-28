package com.he.listeners;

import com.he.presenters.PresenterFactory;
import com.he.context.ApplicationContext;
import com.he.context.ApplicationContextReflectionBased;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new ApplicationContextReflectionBased();
        PresenterFactory factory = new PresenterFactory(sce.getServletContext());
        factory.configure(sce.getServletContext().getRealPath("templates.properties"));
        context.setAttribute("presenterFactory", factory);
        sce.getServletContext().setAttribute("appContext", context);
    }
}
