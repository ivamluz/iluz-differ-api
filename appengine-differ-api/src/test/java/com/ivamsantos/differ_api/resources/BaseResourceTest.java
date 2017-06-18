package com.ivamsantos.differ_api.resources;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivamsantos.differ_api.config.DiffApiModule;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.external.ExternalTestContainerFactory;

/**
 * Created by iluz on 6/17/17.
 */
public class BaseResourceTest extends JerseyTest {
    protected static final String HOST = "http://localhost:8080/";

    protected Injector injector;

    public BaseResourceTest() {
        this.injector = Guice.createInjector(new DiffApiModule());
    }

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder().build();
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new ExternalTestContainerFactory();
    }
}
