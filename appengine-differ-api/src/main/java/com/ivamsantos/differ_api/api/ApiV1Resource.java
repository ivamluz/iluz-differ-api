package com.ivamsantos.differ_api.api;

import com.google.inject.Inject;
import com.ivamsantos.differ_api.diff.resources.DiffV1Resource;

import javax.ws.rs.Path;

/**
 * Created by iluz on 6/16/17.
 */
@Path("v1")
public class ApiV1Resource {
    private final DiffV1Resource diffV1Resource;

    @Inject
    public ApiV1Resource(DiffV1Resource diffV1Resource) {
        this.diffV1Resource = diffV1Resource;
    }

    @Path("diff")
    public DiffV1Resource getDiffV1Resource() {
        return diffV1Resource;
    }
}
