package com.solvd.api.youtube;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InvalidCredentialsTest {
    private  static final Logger logger = LogManager.getLogger(CatVideosTest.class);

    public InvalidCredentialsTest() {}

    @Test
    public void invalidCredentialsTest() {
        InvalidCredentialsMethod invalidCredentials = new InvalidCredentialsMethod();
        Response response = invalidCredentials.callAPIExpectSuccess();
        int statusCode = response.getStatusCode();
        logger.info("Response status code: " + statusCode);
        Assert.assertEquals(statusCode, 400, "Unexpected response status code!");
    }
}
