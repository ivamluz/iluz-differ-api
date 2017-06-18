package com.ivamsantos.differ_api.resources;

import com.google.common.io.BaseEncoding;
import com.ivamsantos.differ_api.diff.model.Differences;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by iluz on 6/17/17.
 */
public class DiffV1ResourceTest extends BaseResourceTest {
    private static final long ID = System.currentTimeMillis();

    private static final String LEFT_PATH = String.format("v1/diff/%s/left", ID);
    private static final String RIGHT_PATH = String.format("v1/diff/%s/right", ID);
    private static final String DIFF_PATH = String.format("v1/diff/%s", ID);

    private static final String DECODED_LEFT = "left";
    private static final String ENCODED_LEFT = BaseEncoding.base64().encode(DECODED_LEFT.getBytes());

    private static final String DECODED_RIGHT = "right";
    private static final String ENCODED_RIGHT = BaseEncoding.base64().encode(DECODED_RIGHT.getBytes());
    public static final String INVALID_PAYLOAD = "foobar==1234";

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldCreateLeftInput() throws URISyntaxException {
        WebResource webResource = client().resource(HOST);

        ClientResponse response = webResource
                .path(LEFT_PATH)
                .post(ClientResponse.class, ENCODED_LEFT);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldNotAcceptInvalidLeftInput() {
        WebResource webResource = client().resource(HOST);

        ClientResponse response = webResource
                .path(LEFT_PATH)
                .post(ClientResponse.class, INVALID_PAYLOAD);

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldCreateRightInput() throws URISyntaxException {
        WebResource webResource = client().resource(HOST);

        ClientResponse response = webResource
                .path(RIGHT_PATH)
                .post(ClientResponse.class, ENCODED_RIGHT);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldNotAcceptInvalidRightInput() throws URISyntaxException {
        WebResource webResource = client().resource(HOST);

        ClientResponse response = webResource
                .path(RIGHT_PATH)
                .post(ClientResponse.class, INVALID_PAYLOAD);

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldCalculateDiff() {
        WebResource webResource = client().resource(HOST);

        webResource
                .path(LEFT_PATH)
                .post(ClientResponse.class, ENCODED_LEFT);

        webResource
                .path(RIGHT_PATH)
                .post(ClientResponse.class, ENCODED_RIGHT);

        Differences differences = webResource
                .path(DIFF_PATH)
                .get(Differences.class);

        assertThat(differences.getCount()).isEqualTo(1);
        assertThat(differences.getDifferences().get(0).getType()).isEqualTo(Differences.Delta.Type.CHANGED);
    }
}
