package com.ivamsantos.differ_api.diff;

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
import com.ivamsantos.differ_api.diff.dao.DiffInputDao;
import com.ivamsantos.differ_api.diff.dao.DiffJobDao;
import com.ivamsantos.differ_api.diff.dao.DiffOutputDao;
import com.ivamsantos.differ_api.diff.model.DiffInput;
import com.ivamsantos.differ_api.diff.model.DiffJob;
import com.ivamsantos.differ_api.diff.model.DiffOutput;
import com.ivamsantos.differ_api.diff.model.Differences;
import com.ivamsantos.differ_api.diff.service.DiffServices;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class DiffApiBaseTest {

    protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy(),
            new LocalMemcacheServiceTestConfig()
    );

    protected DatastoreService datastoreService;
    protected MemcacheService memcacheService;

    protected Injector injector;

    protected Closeable dbSession;

    protected DiffInputDao diffInputDao;
    protected DiffOutputDao diffOutputDao;

    @Deprecated
    protected DiffJobDao diffJobDao;

    protected DiffServices diffServices;

    protected DiffJobFixture diffJobFixture;
    protected DiffInputFixture diffInputFixture;
    protected DiffOutputFixture diffOutputFixture;

    public DiffApiBaseTest() {
        this.injector = Guice.createInjector(new DiffApiModule());
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        ObjectifyService.setFactory(new ObjectifyFactory());

        ObjectifyService.register(DiffJob.class);
        ObjectifyService.register(DiffInput.class);
        ObjectifyService.register(DiffOutput.class);
    }

    @Before
    public void setUp() {
        helper.setUp();
        datastoreService = DatastoreServiceFactory.getDatastoreService();
        memcacheService = MemcacheServiceFactory.getMemcacheService();

        dbSession = ObjectifyService.begin();

        diffInputDao = getInstance(DiffInputDao.class);
        diffJobDao = getInstance(DiffJobDao.class);
        diffOutputDao = getInstance(DiffOutputDao.class);

        diffServices = getInstance(DiffServices.class);

        diffJobFixture = new DiffJobFixture();
        diffInputFixture = new DiffInputFixture();
        diffOutputFixture = new DiffOutputFixture();
    }

    @After
    public void tearDown() {
        dbSession.close();
        helper.tearDown();
    }

    protected <T> T getInstance(Class<T> type) {
        return this.injector.getInstance(type);
    }

    @Deprecated
    public class DiffJobFixture {
        public static final String ORIGINAL_LEFT = "original-left";
        public static final String UPDATED_LEFT = "updated-left";

        public static final String ORIGINAL_RIGHT = "original-right";
        public static final String UPDATED_RIGHT = "updated-right";

        public DiffJob withIdAndLeft(Long id) {
            return new DiffJob.Builder()
                    .withId(id)
                    .withLeft(ORIGINAL_LEFT)
                    .build();
        }

        public DiffJob withIdAndRight(Long id) {
            return new DiffJob.Builder()
                    .withId(id)
                    .withRight(ORIGINAL_RIGHT)
                    .build();
        }

        public DiffJob withIdLeftAndRight(Long id) {
            return new DiffJob.Builder()
                    .withId(id)
                    .withLeft(ORIGINAL_LEFT)
                    .withRight(ORIGINAL_RIGHT)
                    .build();
        }

        public DiffJob full(Long id) {
            return new DiffJob.Builder()
                    .withId(id)
                    .withLeft(ORIGINAL_LEFT)
                    .withRight(ORIGINAL_RIGHT)
                    .withDiff(new Differences())
                    .build();
        }
    }

    public class DiffInputFixture {
        public static final String ORIGINAL_LEFT = "original-left";
        public static final String UPDATED_LEFT = "updated-left";

        public static final String ORIGINAL_RIGHT = "original-right";
        public static final String UPDATED_RIGHT = "updated-right";

        public DiffInput left(Long id) {
            return new DiffInput.Builder()
                    .withId(id)
                    .withSide(DiffInput.Side.LEFT)
                    .withValue(ORIGINAL_LEFT)
                    .build();
        }

        public DiffInput right(Long id) {
            return new DiffInput.Builder()
                    .withId(id)
                    .withSide(DiffInput.Side.RIGHT)
                    .withValue(ORIGINAL_LEFT)
                    .build();
        }
    }

    public class DiffOutputFixture {
        public DiffOutput create(Long id) {
            return new DiffOutput.Builder()
                    .withId(id)
                    .withDifferences(new Differences())
                    .build();
        }
    }
}
