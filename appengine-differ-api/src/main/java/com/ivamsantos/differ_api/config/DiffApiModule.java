package com.ivamsantos.differ_api.config;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.googlecode.objectify.ObjectifyFilter;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators;
import com.ivamsantos.differ_api.api.ApiV1Resource;
import com.ivamsantos.differ_api.api.GenericExceptionMapper;
import com.ivamsantos.differ_api.diff.business.DiffUtilStringDiffer;
import com.ivamsantos.differ_api.diff.business.Differ;
import com.ivamsantos.differ_api.diff.dao.DiffInputDao;
import com.ivamsantos.differ_api.diff.dao.DiffJobDao;
import com.ivamsantos.differ_api.diff.dao.DiffOutputDao;
import com.ivamsantos.differ_api.diff.dao.impl.ObjectifyDiffInputDao;
import com.ivamsantos.differ_api.diff.dao.impl.ObjectifyDiffJobDao;
import com.ivamsantos.differ_api.diff.dao.impl.ObjectifyDiffOutputDao;
import com.ivamsantos.differ_api.diff.model.DiffInput;
import com.ivamsantos.differ_api.diff.model.DiffJob;
import com.ivamsantos.differ_api.diff.model.DiffOutput;
import com.ivamsantos.differ_api.diff.resources.DiffV1Resource;
import com.ivamsantos.differ_api.diff.service.DiffServices;
import com.ivamsantos.differ_api.diff.service.DiffServicesInputModelImpl;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Guice module for dependency injection.
 */
public class DiffApiModule extends AbstractModule {

    // Objectify entities should be registered here.
    static {
        JodaTimeTranslators.add(ObjectifyService.factory());

        ObjectifyService.register(DiffInput.class);
        ObjectifyService.register(DiffOutput.class);
        ObjectifyService.register(DiffJob.class);
    }

    @Override
    protected void configure() {
        bindApiResourceClasses();
        bindDaoClasses();
        bindBusinessClasses();
        bindServicesClasses();
        bindInfrastructure();
    }

    private void bindInfrastructure() {
        bind(GuiceContainer.class);
        bind(ObjectifyFilter.class).in(Scopes.SINGLETON);
    }

    /*
     * Web resource binding
     */
    private void bindApiResourceClasses() {
        bind(ApiV1Resource.class).in(Scopes.SINGLETON);
        bind(DiffV1Resource.class).in(Scopes.SINGLETON);
        bind(GenericExceptionMapper.class).in(Scopes.SINGLETON);
    }

    /*
     * DAO binding
     */
    private void bindDaoClasses() {
        bind(DiffJobDao.class).to(ObjectifyDiffJobDao.class);
        bind(DiffInputDao.class).to(ObjectifyDiffInputDao.class);
        bind(DiffOutputDao.class).to(ObjectifyDiffOutputDao.class);
    }

    /**
     * Business classes binding
     */
    private void bindBusinessClasses() {
        bind(Differ.class).to(DiffUtilStringDiffer.class);
    }

    /*
     * Service classes binding
     */
    private void bindServicesClasses() {
        bind(DiffServices.class).to(DiffServicesInputModelImpl.class);
    }

    @Provides
    @Singleton
    protected DatastoreService datastoreServiceProvider() {
        return DatastoreServiceFactory.getDatastoreService();
    }

    @Provides
    @Singleton
    private MemcacheService memcacheServiceProvider() {
        return MemcacheServiceFactory.getMemcacheService();
    }
}
