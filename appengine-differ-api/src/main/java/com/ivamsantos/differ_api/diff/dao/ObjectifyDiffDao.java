package com.ivamsantos.differ_api.diff.dao;

import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.Diff;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by iluz on 6/15/17.
 */
public class ObjectifyDiffDao implements DiffDao {
    @Override
    public Long save(Diff diff) {
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

    @Override
    public void deleteById(Long id) {
        ofy().delete().type(Diff.class).id(id).now();
    }

    @Override
    public Diff transact(final DoWork work) {
        if (work == null) {
            return null;
        }

        return ofy().transact(new Work<Diff>() {
            public Diff run() {
                return work.run();
            }
        });
    }

    @Override
    public void transact(final DoVoidWork work) {
        if (work == null) {
            return;
        }

        ofy().transact(new VoidWork() {
            public void vrun() {
                work.run();
            }
        });

        work.run();
    }
}
