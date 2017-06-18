package commons;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivamsantos.differ_api.config.DiffApiModule;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.external.ExternalTestContainerFactory;

/**
 * Base class for performing integration tests againt the API.
 */
public class BaseResourceTest extends JerseyTest {
    // TODO: make this value configurable, so the tests may run against environments other than localhost.
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
