package com.solvd.api.youtube;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class YoutubeAPITest {
    private static final Logger logger = LogManager.getLogger(YoutubeAPITest.class);

    @Test
    public void testSearchCatVideos() {
        SearchVideosMethod search = new SearchVideosMethod("cat videos");
        Response response = search.callAPIExpectSuccess();
        search.validateResponseAgainstSchema("api/youtube/_get/search_rs.schema");
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JSONArray itemsArray = jsonResponse.getJSONArray("items");
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject video = itemsArray.getJSONObject(i);
            JSONObject snippet = video.getJSONObject("snippet");
            SoftAssert softAssert = new SoftAssert();

            String uploadDate = snippet.getString("publishedAt");
            softAssert.assertNotNull(uploadDate, "Upload date not found for video: " + video.getJSONObject("id").getString("videoId"));

            String description = snippet.getString("description");
            softAssert.assertNotNull(description, "Description not found for video: " + video.getJSONObject("id").getString("videoId"));

            String title = snippet.getString("title");
            softAssert.assertNotNull(title, "Title not found for video: " + video.getJSONObject("id").getString("videoId"));

            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
            softAssert.assertTrue(thumbnails.length() > 0, "Thumbnail URLs not found for video: " + video.getJSONObject("id").getString("videoId"));
            softAssert.assertAll();
        }
        logger.info("searchCatVideos test completed successfully.");
    }

    @Test
    public void testJavaBeginnersVideosAndComments() {
        SearchVideosMethod search = new SearchVideosMethod("java beginners");
        Response response = search.callAPIExpectSuccess();
        search.validateResponseAgainstSchema("api/youtube/_get/search_rs.schema");
        GetVideoCommentsMethod comments = new GetVideoCommentsMethod("eIrMbAQSU34");
        Response commentsResponse = comments.callAPIExpectSuccess();
        List<Map<String, Object>> items = commentsResponse.jsonPath().getList("items");
        Assert.assertTrue(items.size() > 0, "No comments found for the video");
        SoftAssert softAssert = new SoftAssert();

        for (int i = 0; i < items.size(); i++) {
            String commentText = commentsResponse.jsonPath().getString("items[" + i + "].snippet.topLevelComment.snippet.textDisplay");
            softAssert.assertNotNull(commentText, "Comment text not found for comment index " + i);

            String authorInfo = commentsResponse.jsonPath().getString("items[" + i + "].snippet.topLevelComment.snippet.authorDisplayName");
            softAssert.assertNotNull(authorInfo, "Comment author information not found for comment index " + i);

            Integer numLikes = commentsResponse.jsonPath().getInt("items[" + i + "].snippet.topLevelComment.snippet.likeCount");
            softAssert.assertNotNull(numLikes, "Number of likes not found for comment index " + i);
            softAssert.assertAll();
        }
        logger.info("Comments validation completed successfully.");
    }

    @Test
    public void testSearchWithInvalidCredentialsTest() {
        InvalidCredentialsMethod invalidCredentials = new InvalidCredentialsMethod();
        Response response = invalidCredentials.callAPI();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400, "Unexpected response status code!");
    }
}
