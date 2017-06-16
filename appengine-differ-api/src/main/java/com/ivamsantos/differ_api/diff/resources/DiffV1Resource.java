package com.ivamsantos.differ_api.diff.resources;

import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.Diff;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST API for performing diff operations.
 * <p>
 * Created by iluz on 6/15/17.
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Singleton
public class DiffV1Resource {
    @GET
    @Path("/{id : [0-9]+}/left")
    public Response left(@PathParam("id") final Long id) throws InvalidDiffObjectException {
        Diff diff = new Diff.Builder().withId(id).withLeft("left").build();
        return Response.status(200).entity(diff).build();
    }

    @GET
    @Path("/{id : [0-9]+}/right")
    public Response right(@PathParam("id") final Long id) throws InvalidDiffObjectException {
        Diff diff = new Diff.Builder().withId(id).withRight("right").build();
        return Response.status(200).entity(diff).build();
    }

    @GET
    @Path("/{id : [0-9]+}")
    public Response getDifferences(@PathParam("id") final Long id) throws InvalidDiffObjectException {
        Diff diff = new Diff.Builder().withId(id).withLeft("left").withRight("right").withDiff("diff").build();
        return Response.status(200).entity(diff).build();
    }
}
