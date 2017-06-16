package com.ivamsantos.differ_api;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.ivamsantos.differ_api.config.DiffApiModule;
import com.ivamsantos.differ_api.diff.dao.DiffDao;
import com.ivamsantos.differ_api.diff.model.Diff;
import com.ivamsantos.differ_api.diff.service.DiffServices;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class DiffApiBaseTest {

    final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy(),
            new LocalMemcacheServiceTestConfig()
    );

    protected DatastoreService datastoreService;
    protected MemcacheService memcacheService;

    protected Injector injector;

    protected Closeable dbSession;

    public DiffApiBaseTest() {
        this.injector = Guice.createInjector(new DiffApiModule());
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        ObjectifyService.setFactory(new ObjectifyFactory());
        ObjectifyService.register(Diff.class);
    }

    @Before
    public void setUp() {
        helper.setUp();
        datastoreService = DatastoreServiceFactory.getDatastoreService();
        memcacheService = MemcacheServiceFactory.getMemcacheService();

        dbSession = ObjectifyService.begin();
    }

    @After
    public void tearDown() {
        dbSession.close();
        helper.tearDown();
    }

    protected <T> T getInstance(Class<T> type) {
        return this.injector.getInstance(type);
    }

    protected DiffDao diffDao() {
        return getInstance(DiffDao.class);
    }

    protected DiffServices diffServices() {
        return getInstance(DiffServices.class);
    }
}
