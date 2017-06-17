package com.ivamsantos.differ_api.diff.dao;

import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.DiffJob;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by iluz on 6/15/17.
 */
public class ObjectifyDiffJobDao implements DiffJobDao {
    @Override
    public Long save(DiffJob diffJob) {
        if (!diffJob.hasId()) {
            throw new InvalidDiffObjectException("withId can't be null nor empty.");
        }

        return ofy().save().entity(diffJob).now().getId();
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

    @Override
    public DiffJob transact(final DoWork work) {
        if (work == null) {
            return null;
        }

        return ofy().transact(new Work<DiffJob>() {
            public DiffJob run() {
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
