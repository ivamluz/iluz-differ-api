package com.ivamsantos.differ_api.diff.resources;

import com.ivamsantos.differ_api.diff.business.DiffUtilStringDiffer;
import com.ivamsantos.differ_api.diff.business.Differ;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.Diff;
import com.ivamsantos.differ_api.diff.model.Differences;

import javax.inject.Singleton;
import javax.ws.rs.*;
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
    @POST
    @Path("/{id : [0-9]+}/left")
    public Response left(@PathParam("id") final Long id, String body) {
        Diff diff = new Diff.Builder().withId(id).withLeft(body).build();
        return Response.status(200).entity(diff).build();
    }

    @POST
    @Path("/{id : [0-9]+}/right")
    public Response right(@PathParam("id") final Long id, String body) {
        Diff diff = new Diff.Builder().withId(id).withRight(body).build();
        return Response.status(200).entity(diff).build();
    }

    @GET
    @Path("/{id : [0-9]+}")
    public Response getDifferences(@PathParam("id") final Long id) {
        final String original = "Line 1\n" +
                "Line 2\n" +
                "Line 3\n" +
                "Line 4\n" +
                "Line 5\n" +
                "Line 6\n" +
                "Line 7\n" +
                "Line 8\n" +
                "Line 9\n" +
                "Line 10";

        final String revised = "Line 2\n" +
                "Line 3 with changes\n" +
                "Line 4\n" +
                "Line 5 with changes and\n" +
                "a new line\n" +
                "Line 6\n" +
                "new line 6.1\n" +
                "Line 7\n" +
                "Line 8\n" +
                "Line 9\n" +
                "Line 10 with changes";

        Differ differ = new DiffUtilStringDiffer(original, revised);
        Differences differences = differ.getDifferences();

        return Response.status(200).entity(differences).build();
    }
}
