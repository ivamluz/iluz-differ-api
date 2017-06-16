package com.ivamsantos.differ_api.config;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.googlecode.objectify.ObjectifyFilter;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators;
import com.ivamsantos.differ_api.api.GenericExceptionMapper;
import com.ivamsantos.differ_api.diff.dao.DiffDao;
import com.ivamsantos.differ_api.diff.dao.ObjectifyDiffDao;
import com.ivamsantos.differ_api.diff.model.Diff;
import com.ivamsantos.differ_api.diff.resources.DiffResource;
import com.ivamsantos.differ_api.diff.service.DiffServices;
import com.ivamsantos.differ_api.diff.service.DiffServicesImpl;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;


public class DiffApiModule extends AbstractModule {

    static {
        JodaTimeTranslators.add(ObjectifyService.factory());

        ObjectifyService.register(Diff.class);
    }

    @Override
    protected void configure() {
        bindApiResourceClasses();
        bindDAOClasses();
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
        bind(DiffResource.class);
        bind(GenericExceptionMapper.class);
    }

    /*
     * DAO binding
     */
    private void bindDAOClasses() {
        bind(DiffDao.class).to(ObjectifyDiffDao.class);
    }

    /*
     * Business services binding
     */
    private void bindServicesClasses() {
        bind(DiffServices.class).to(DiffServicesImpl.class);

    }

    @Provides
    protected DatastoreService datastoreServiceProvider() {
        return DatastoreServiceFactory.getDatastoreService();
    }

    @Provides
    private MemcacheService memcacheServiceProvider() {
        return MemcacheServiceFactory.getMemcacheService();
    }
}
