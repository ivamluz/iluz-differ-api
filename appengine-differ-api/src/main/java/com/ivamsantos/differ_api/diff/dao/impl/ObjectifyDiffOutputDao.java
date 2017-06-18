package com.ivamsantos.differ_api.diff.dao.impl;

import com.ivamsantos.differ_api.diff.dao.DiffOutputDao;
import com.ivamsantos.differ_api.diff.model.DiffOutput;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by iluz on 6/18/17.
 */
public class ObjectifyDiffOutputDao extends AbstractObjectifyDao<DiffOutput> implements DiffOutputDao {
    @Override
    public void save(DiffOutput diffOutput) {
        ofy().save().entity(diffOutput).now().getId();
    }

    @Override
    public DiffOutput findById(Long id) {
        return ofy().load().type(DiffOutput.class).id(id).now();
    }

    @Override
    public void delete(DiffOutput diffOutput) {
        ofy().delete().entity(diffOutput).now();
    }

    @Override
    public void deleteById(Long id) {
        ofy().delete().type(DiffOutput.class).id(id).now();
    }
}
