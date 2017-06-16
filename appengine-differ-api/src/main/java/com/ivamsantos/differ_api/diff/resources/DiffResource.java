package com.ivamsantos.differ_api.diff.resources;

import com.ivamsantos.differ_api.diff.model.Diff;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST API for performing diff operations.
 * <p>
 * Created by iluz on 6/15/17.
 */
@Path("diff")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Singleton
public class DiffResource {
    @GET
    public Diff search(@QueryParam("withId") final Long id) {
        return new Diff.Builder().withId(id).build();
    }
}
