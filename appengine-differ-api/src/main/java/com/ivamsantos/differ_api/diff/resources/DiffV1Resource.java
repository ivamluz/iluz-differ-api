package com.ivamsantos.differ_api.diff.resources;

import com.google.common.io.BaseEncoding;
import com.google.inject.Inject;
import com.ivamsantos.differ_api.diff.model.Differences;
import com.ivamsantos.differ_api.diff.service.DiffServices;

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
    private DiffServices diffServices;

    @Inject
    public DiffV1Resource(DiffServices diffServices) {
        this.diffServices = diffServices;
    }

    @POST
    @Path("/{id : [0-9]+}/left")
    public Response left(@PathParam("id") final Long id, String encodedLeft) {
        String decodedLeft;
        try {
            decodedLeft = new String(BaseEncoding.base64().decode(encodedLeft));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The left input should be a valid base64 encoded value.", e);
        }

        diffServices.saveLeft(id, decodedLeft);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/{id : [0-9]+}/right")
    public Response right(@PathParam("id") final Long id, String encodedRight) {
        String decodeRight;
        try {
            decodeRight = new String(BaseEncoding.base64().decode(encodedRight));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The right input should be a valid base64 encoded value.", e);
        }

        diffServices.saveRight(id, decodeRight);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id : [0-9]+}")
    public Response getDifferences(@PathParam("id") final Long id) {
        Differences differences = diffServices.diff(id);

        return Response.status(Response.Status.OK).entity(differences).build();
    }
}
