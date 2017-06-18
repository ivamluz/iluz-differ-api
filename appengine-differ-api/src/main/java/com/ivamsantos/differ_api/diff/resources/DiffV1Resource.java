package com.ivamsantos.differ_api.diff.resources;

import com.google.inject.Inject;
import com.ivamsantos.differ_api.diff.model.Differences;
import com.ivamsantos.differ_api.diff.service.DiffServices;
import com.sun.jersey.core.util.Base64;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Version 1 of the REST API for performing diff operations.
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Singleton
public class DiffV1Resource {
    private DiffServices diffServices;

    @Inject
    public DiffV1Resource(DiffServices diffServices) {
        this.diffServices = diffServices;
    }

    /**
     * Stores the left side of the operation for later processing.
     * <p>
     * It may return @{@link Response.Status#BAD_REQUEST} instead (thanks to @{@link com.ivamsantos.differ_api.api.GenericExceptionMapper},
     * in case the decoded data is larger than 1mb.
     *
     * @param id    long
     * @param input Input.
     * @return
     */
    @POST
    @Path("/{id : [0-9]+}/left")
    public Response left(@PathParam("id") final long id, Input input) {
        String decodedLeft = Base64.base64Decode(input.data);
        diffServices.saveLeft(id, decodedLeft);

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Stores the right side of the operation for later processing.
     * <p>
     * It may return @{@link Response.Status#BAD_REQUEST} instead (thanks to @{@link com.ivamsantos.differ_api.api.GenericExceptionMapper},
     * in case the decoded data is larger than 1mb.
     *
     * @param id    long
     * @param input Input.
     * @return
     */

    @POST
    @Path("/{id : [0-9]+}/right")
    public Response right(@PathParam("id") final long id, Input input) {
        String decodeRight = Base64.base64Decode(input.data);
        diffServices.saveRight(id, decodeRight);

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Calculates the differences between the previously saved left and right sides and return a JSON representation
     * of @{@link Differences}
     * <p>
     * It may return @{@link Response.Status#BAD_REQUEST} instead (thanks to @{@link com.ivamsantos.differ_api.api.GenericExceptionMapper},
     * in case either (or both) sides of the comparison is not available.
     *
     * @param id long
     * @return
     */
    @GET
    @Path("/{id : [0-9]+}")
    public Response getDifferences(@PathParam("id") final long id) {
        Differences differences = diffServices.diff(id);

        return Response.status(Response.Status.OK).entity(differences).build();
    }

    /**
     * Object for encapsulating parameters passed to left() and right() methods.
     */
    public static class Input {
        /**
         * Base64 encoded String for diff-ing.
         */
        private String data;

        public Input() {

        }

        public Input(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }
}
