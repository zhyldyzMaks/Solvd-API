package com.solvd.api.youtube;

import com.zebrunner.carina.utils.config.Configuration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class JavaBeginnersVideoTest {
    private  static final Logger logger = LogManager.getLogger(JavaBeginnersVideoTest.class);

    public JavaBeginnersVideoTest(){}

    @Test
    public void javaBeginnersVideosAndComments() {
        JavaBeginnersVideosMethod search = new JavaBeginnersVideosMethod();
        Response response = search.callAPIExpectSuccess();
        String videoId = response.path("items[0].id.videoId");
        logger.info("Video ID for the first video: {}", videoId);

        Response commentsResponse = getVideoComments(videoId);
        validateCommentsResponse(commentsResponse);
    }

    private Response getVideoComments(String videoId) {
        String apiUrl = "https://www.googleapis.com/youtube/v3/commentThreads";
        String apiKey = Configuration.getRequired("youtube.api.key");
        String part = "snippet,replies";
        int maxResults = 5;

        Response response = RestAssured.given()
                .queryParam("part", part)
                .queryParam("videoId", videoId)
                .queryParam("maxResults", maxResults)
                .queryParam("key", apiKey)
                .get(apiUrl);
        logger.info("Comments API status: {}", response.getStatusCode());
        return response;
    }

    private void validateCommentsResponse(Response commentsResponse) {
        List<Map<String, Object>> items = commentsResponse.jsonPath().getList("items");
        Assert.assertTrue(items.size() > 0, "No comments found for the video");

        for (int i = 0; i < items.size(); i++) {
            String commentText = commentsResponse.jsonPath().getString("items[" + i + "].snippet.topLevelComment.snippet.textDisplay");
            Assert.assertNotNull(commentText, "Comment text not found for comment index " + i);

            String authorInfo = commentsResponse.jsonPath().getString("items[" + i + "].snippet.topLevelComment.snippet.authorDisplayName");
            Assert.assertNotNull(authorInfo, "Comment author information not found for comment index " + i);

            Integer numLikes = commentsResponse.jsonPath().getInt("items[" + i + "].snippet.topLevelComment.snippet.likeCount");
            Assert.assertNotNull(numLikes, "Number of likes not found for comment index " + i);
        }
        logger.info("Comments validation successful.");
    }
}
