package com.ivamsantos.diff.resources;

import com.ivamsantos.diff.model.Diff;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by iluz on 6/15/17.
 */
@Path("diff")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DiffResource {
    @GET
    public Diff search(@QueryParam("id") final String id) {
        return new Diff.Builder().id(id).build();
    }
}
