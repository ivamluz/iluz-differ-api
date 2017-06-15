package com.ivamsantos.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceFactory {

    private static final Injector injector = Guice.createInjector(new DifferApiServletModule());

    public static Injector getInjector() {
        return injector;
    }

}
