package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.Diff;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by iluz on 6/15/17.
 */
public class ObjectifyDiffDao implements DiffDao {
    @Override
    public Long insert(Diff diff) throws InvalidDiffObjectException {
        if (!diff.hasId()) {
            throw new InvalidDiffObjectException("withId can't be null nor empty.");
        }

        return ofy().save().entity(diff).now().getId();
    }

    @Override
    public Diff findById(Long id) {
        return ofy().load().type(Diff.class).id(id).now();
    }

    @Override
    public void delete(Diff diff) {
        ofy().delete().entity(diff).now();
    }
}
