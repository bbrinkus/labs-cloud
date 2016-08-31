package com.brinkus.labs.cloud.service.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestControllerBaseTest {

    private RestController restController;

    private class RestController extends RestControllerBase {

        private boolean executed;

        public ResponseEntity getResponseFromRequest(Object o) {
            return createResponse(() -> {
                executed = true;
                return o;
            });
        }

        public ResponseEntity getResponseFromException() {
            return createResponse(() -> {
                throw new IllegalStateException("Response exception");
            });
        }

        public ResponseEntity getResponseFromNullSupplier() {
            return createResponse(null);
        }

        public boolean isExecuted() {
            return executed;
        }

    }

    @Before
    public void before() throws Exception {
        restController = new RestController();
    }

    @After
    public void after() throws Exception {
        restController = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResponseFromNullSupplier() {
        restController.getResponseFromNullSupplier();
    }

    @Test(expected = IllegalStateException.class)
    public void getResponseFromException() {
        restController.getResponseFromException();
    }

    @Test
    public void getObjectResponse() {
        Object param = new Object();
        ResponseEntity response = restController.getResponseFromRequest(param);
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(param));
    }

    @Test
    public void getPrimitiveTypeResponse() {
        int param = 1;
        ResponseEntity response = restController.getResponseFromRequest(param);
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(param));
    }

    @Test
    public void getNullObjectResponse() {
        ResponseEntity response = restController.getResponseFromRequest(null);
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(response.getBody(), nullValue());
    }

    @Test
    public void getListResponse() {
        List<Object> param = Arrays.asList(new Object());
        ResponseEntity response = restController.getResponseFromRequest(param);
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(param));
    }

    @Test
    public void getEmptyListResponse() {
        ResponseEntity response = restController.getResponseFromRequest(new ArrayList<String>());
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(response.getBody(), nullValue());
    }

    @Test
    public void getStringArrayResponse() {
        String[] param = { "a" };
        ResponseEntity response = restController.getResponseFromRequest(param);
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(param));
    }

    @Test
    public void getEmptyStringArrayResponse() {
        ResponseEntity response = restController.getResponseFromRequest(new String[0]);
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(response.getBody(), nullValue());
    }

    @Test
    public void getIntegerArrayResponse() {
        int[] param = { 1 };
        ResponseEntity response = restController.getResponseFromRequest(param);
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(param));
    }

    @Test
    public void getEmptyIntegerArrayResponse() {
        ResponseEntity response = restController.getResponseFromRequest(new int[0]);
        assertThat(restController.isExecuted(), is(true));
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(response.getBody(), nullValue());
    }

}
