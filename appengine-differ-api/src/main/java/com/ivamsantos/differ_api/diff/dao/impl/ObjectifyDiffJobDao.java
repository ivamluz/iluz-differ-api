package com.ivamsantos.differ_api.diff.dao.impl;

import com.ivamsantos.differ_api.diff.dao.DiffJobDao;
import com.ivamsantos.differ_api.diff.model.DiffJob;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by iluz on 6/15/17.
 */
@Deprecated
public class ObjectifyDiffJobDao extends AbstractObjectifyDao<DiffJob> implements DiffJobDao {
    @Override
    public void save(DiffJob diffJob) {
        ofy().save().entity(diffJob).now().getId();
    }

    @Override
    public DiffJob findById(Long id) {
        return ofy().load().type(DiffJob.class).id(id).now();
    }

    @Override
    public void delete(DiffJob diffJob) {
        ofy().delete().entity(diffJob).now();
    }

    @Override
    public void deleteById(Long id) {
        ofy().delete().type(DiffJob.class).id(id).now();
    }
}
