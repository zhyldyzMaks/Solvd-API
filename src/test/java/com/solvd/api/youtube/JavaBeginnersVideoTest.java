package com.solvd.api.youtube;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class JavaBeginnersVideoTest {
    private  static final Logger logger = LogManager.getLogger(JavaBeginnersVideoTest.class);

    @Test
    public void javaBeginnersVideosAndComments() {
        JavaBeginnersVideosMethod search = new JavaBeginnersVideosMethod();
        Response response = search.callAPIExpectSuccess();
        search.validateResponseAgainstSchema("youtube/_get/rs.schema");
        String videoId = response.path("items[0].id.videoId");
        logger.info("Video ID for the first video: {}", videoId);
        Response commentsResponse = search.getVideoComments(videoId);
        search.validateCommentsResponse(commentsResponse);
    }
}
