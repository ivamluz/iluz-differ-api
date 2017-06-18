package com.ivamsantos.differ_api.config;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import java.util.logging.Logger;

/**
 * Listener responsible for initializing the injector.
 */
public class DifferApiGuiceServletContextListener extends GuiceServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(DifferApiGuiceServletContextListener.class.getName());

    @Override
    protected Injector getInjector() {
        final Injector injector = GuiceFactory.getInjector();

        final int countBindings = injector.getAllBindings().size();
        LOGGER.info("There are " + countBindings + " bindings");

        return injector;
    }
}
