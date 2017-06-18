package com.ivamsantos.differ_api.api;

import com.googlecode.objectify.NotFoundException;
import com.ivamsantos.differ_api.api.exception.EntityNotFoundException;
import com.ivamsantos.differ_api.api.model.ErrorResponse;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception mapper used for returning custom API error responses.
 */
@Provider
@Singleton
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GenericExceptionMapper.class.getSimpleName());

    @Context
    HttpServletRequest request;

    @Override
    public Response toResponse(Throwable ex) {

        LOGGER.log(Level.SEVERE, "Got this unexpected exception. Converting to API error.", ex);

        ErrorResponse errorMessage = new ErrorResponse();
        errorMessage.setMessage(ex.getMessage());
        setHttpStatus(ex, errorMessage);
        setErrorCode(ex, errorMessage);

        if (request.getParameter("debug") != null) {
            StringWriter errorStackTrace = new StringWriter();
            ex.printStackTrace(new PrintWriter(errorStackTrace));
            errorMessage.setDeveloperMessage(errorStackTrace.toString());
        }

        return Response.status(errorMessage.getStatus()).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
    }

    private void setErrorCode(Throwable ex, ErrorResponse errorMessage) {
        // TODO: Proper error code mapping.
        errorMessage.setCode("1");
    }

    private void setHttpStatus(Throwable ex, ErrorResponse errorMessage) {
        if (ex instanceof WebApplicationException) {
            errorMessage.setStatus(((WebApplicationException) ex).getResponse().getStatus());
        } else if (causedByEntityNotFoundException(ex)) {
            errorMessage.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        } else if (ex instanceof NotFoundException) {
            errorMessage.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        } else if (ex instanceof IllegalArgumentException) {
            errorMessage.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        } else if (ex instanceof InvalidDiffInputException) {
            errorMessage.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        } else {
            // defaults to internal server error 500
            errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }

    private boolean causedByEntityNotFoundException(Throwable ex) {
        return ex instanceof EntityNotFoundException
                || (ex.getCause() != null && ex.getCause() instanceof EntityNotFoundException);
    }
}