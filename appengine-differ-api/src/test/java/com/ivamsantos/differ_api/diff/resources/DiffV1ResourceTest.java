package com.ivamsantos.differ_api.diff.resources;

import com.ivamsantos.differ_api.api.model.ErrorResponse;
import com.ivamsantos.differ_api.diff.model.Differences;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;
import commons.BaseResourceTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

import static com.google.common.truth.Truth.assertThat;
import static commons.TestHelper.hugeString;
import static commons.TestHelper.randomId;

/**
 * Test suite for DiffV1Resource.
 * <p>
 * Created by iluz on 6/17/17.
 */
public class DiffV1ResourceTest extends BaseResourceTest {
    private static final long ID = System.currentTimeMillis();

    public static final String BASE_LEFT_PATH = "v1/diff/%s/left";
    public static final String BASE_RIGHT_PATH = "v1/diff/%s/right";
    public static final String BASE_RESULTS_PATH = "v1/diff/%s";

    private static final String LEFT_PATH = String.format(BASE_LEFT_PATH, ID);
    private static final String RIGHT_PATH = String.format(BASE_RIGHT_PATH, ID);
    private static final String DIFF_PATH_WITH_VALID_ID = String.format(BASE_RESULTS_PATH, ID);

    private static final String DECODED_LEFT = "left";
    private static final String ENCODED_LEFT = new String(Base64.encode(DECODED_LEFT));

    private static final String DECODED_RIGHT = "right";
    private static final String ENCODED_RIGHT = new String(Base64.encode(DECODED_RIGHT));

    private WebResource webResource;

    public DiffV1ResourceTest() {
        webResource = client().resource(HOST);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldCreateLeftInput() throws URISyntaxException {
        ClientResponse response = webResource
                .path(LEFT_PATH)
                .post(ClientResponse.class, ENCODED_LEFT);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldCreateRightInput() throws URISyntaxException {
        ClientResponse response = webResource
                .path(RIGHT_PATH)
                .post(ClientResponse.class, ENCODED_RIGHT);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldCalculateDiff() {
        webResource
                .path(LEFT_PATH)
                .post(ENCODED_LEFT);

        webResource
                .path(RIGHT_PATH)
                .post(ENCODED_RIGHT);

        Differences differences = webResource
                .path(DIFF_PATH_WITH_VALID_ID)
                .get(Differences.class);

        assertThat(differences.getCount()).isEqualTo(1);
        assertThat(differences.getDifferences().get(0).getType()).isEqualTo(Differences.Delta.Type.CHANGED);
    }

    @Test
    public void shouldReturnBadRequestIfBothLeftAndRightAreMissing() {
        String randomDiffPath = String.format(BASE_RESULTS_PATH, randomId());

        ClientResponse response = webResource
                .path(randomDiffPath)
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertThat(errorResponse.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestIfLeftIsMissing() {
        long id = randomId();

        String rightPath = String.format(BASE_RIGHT_PATH, id);
        webResource
                .path(rightPath)
                .post(ENCODED_RIGHT);

        String resultsPath = String.format(BASE_RESULTS_PATH, id);
        ClientResponse response = webResource
                .path(resultsPath)
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertThat(errorResponse.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestIfRightIsMissing() {
        long id = randomId();

        String leftPath = String.format(BASE_LEFT_PATH, id);
        webResource
                .path(leftPath)
                .post(ENCODED_LEFT);

        String resultsPath = String.format(BASE_RESULTS_PATH, id);
        ClientResponse response = webResource
                .path(resultsPath)
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertThat(errorResponse.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestIfLeftInputIsTooLarge() {
        long id = randomId();

        String rightPath = String.format(BASE_LEFT_PATH, id);
        ClientResponse response = webResource
                .path(rightPath)
                .post(ClientResponse.class, hugeString());

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertThat(errorResponse.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestIfRightInputIsTooLarge() {
        long id = randomId();

        String rightPath = String.format(BASE_RIGHT_PATH, id);
        ClientResponse response = webResource
                .path(rightPath)
                .post(ClientResponse.class, hugeString());

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertThat(errorResponse.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }
}
