package com.ivamsantos.differ_api.config;

import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the entry module registered through GuiceFactory. It's responsible for registering the app modules and
 * configuring Jersey params.
 */
public class DifferApiServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        install(new DiffApiModule());

        // Objectify requires a filter to clean up any thread-local transaction contexts and pending asynchronous
        // operations that remain at the end of a request: https://code.google.com/p/objectify-appengine/wiki/Setup
        filter("/*").through(ObjectifyFilter.class);

        filterRegex("/(?!_ah).*").through(GuiceContainer.class, jerseyInitParams());
    }

    private Map<String, String> jerseyInitParams() {
        Map<String, String> jerseyGuiceInitParams = new HashMap<String, String>();
        jerseyGuiceInitParams.put(GuiceContainer.JSP_TEMPLATES_BASE_PATH, "/WEB-INF/jsp");
        jerseyGuiceInitParams.put(GuiceContainer.PROPERTY_WEB_PAGE_CONTENT_REGEX,
                "(/_ah/?.*)|(/.*\\.jsp)");
        return jerseyGuiceInitParams;
    }
}
