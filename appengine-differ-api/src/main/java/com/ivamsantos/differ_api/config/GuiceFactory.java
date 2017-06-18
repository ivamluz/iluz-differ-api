package com.ivamsantos.differ_api.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Factory responsible for creating the dependency injector.
 */
public class GuiceFactory {

    private static final Injector injector = Guice.createInjector(new DifferApiServletModule());

    public static Injector getInjector() {
        return injector;
    }

}
