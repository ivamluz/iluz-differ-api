package com.ivamsantos.differ_api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link HelloAppEngine}.
 */

@RunWith(JUnit4.class)
public class HelloAppEngineTest extends DiffApiBaseTest {
    private static final String FAKE_URL = "fake.fk/hello";

    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    private StringWriter responseWriter;
    private HelloAppEngine servletUnderTest;

    @Before
    @Override
    public void setUp() {
        super.setUp();

        MockitoAnnotations.initMocks(this);
        helper.setUp();

        //  Set up some fake HTTP requests
        when(mockRequest.getRequestURI()).thenReturn(FAKE_URL);

        // Set up a fake HTTP response.
        responseWriter = new StringWriter();

        try {
            when(mockResponse.getWriter()).thenReturn(new PrintWriter(responseWriter));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        servletUnderTest = new HelloAppEngine();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void doGet_writesResponse() throws Exception {
        servletUnderTest.doGet(mockRequest, mockResponse);

        // We expect our hello world response.
        assertThat(responseWriter.toString())
                .named("HelloAppEngine response")
                .contains("Hello App Engine - Standard ");
    }

    @Test
    public void HelloInfo_test() {
        String result = HelloAppEngine.getInfo();
        assertThat(result)
                .named("HelloAppEngine.getInfo")
                .containsMatch("^Version:\\s+.+OS:\\s+.+User:\\s");
    }
}
