package com.epam.esm;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class[] getServletConfigClasses() {
        return new Class[] { SpringApiConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Class[] getRootConfigClasses() {
        return new Class[] {};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.active", "prod");
        super.onStartup(servletContext);
    }
}
