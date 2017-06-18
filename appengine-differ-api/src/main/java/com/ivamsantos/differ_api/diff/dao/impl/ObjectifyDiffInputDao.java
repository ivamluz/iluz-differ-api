package com.ivamsantos.differ_api.diff.dao.impl;

import com.google.apphosting.api.ApiProxy;
import com.ivamsantos.differ_api.diff.dao.DiffInputDao;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.DiffInput;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by iluz on 6/18/17.
 */
public class ObjectifyDiffInputDao extends AbstractObjectifyDao<DiffInput> implements DiffInputDao {
    @Override
    public void save(DiffInput diffInput) {
        try {
            ofy().save().entity(diffInput).now();
        } catch (ApiProxy.RequestTooLargeException e) {
            throw new InvalidDiffInputException("Maximum allowed input size is 1mb.");
        }
    }

    @Override
    public List<DiffInput> findById(long id) {
        return ofy().load().type(DiffInput.class).filter("id = ", id).list();
    }

    @Override
    public DiffInput findByKey(String key) {
        return ofy().load().type(DiffInput.class).id(key).now();
    }

    @Override
    public void delete(DiffInput diffInput) {
        ofy().delete().entity(diffInput).now();
    }

    @Override
    public void deleteByKey(String key) {
        ofy().delete().type(DiffInput.class).id(key).now();
    }
}
