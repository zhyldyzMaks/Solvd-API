package com.solvd.api.youtube;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CatVideosTest {
    private  static final Logger logger = LogManager.getLogger(CatVideosTest.class);

    public CatVideosTest(){}

    @Test
    public void searchCatVideos() {
        SearchCatVideosMethod search = new SearchCatVideosMethod();
        Response response = search.callAPIExpectSuccess();
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JSONArray itemsArray = jsonResponse.getJSONArray("items");
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject video = itemsArray.getJSONObject(i);
            JSONObject snippet = video.getJSONObject("snippet");

            String uploadDate = snippet.getString("publishedAt");
            Assert.assertNotNull(uploadDate, "Upload date not found for video: " + video.getJSONObject("id").getString("videoId"));

            String description = snippet.getString("description");
            Assert.assertNotNull(description, "Description not found for video: " + video.getJSONObject("id").getString("videoId"));

            String title = snippet.getString("title");
            Assert.assertNotNull(title, "Title not found for video: " + video.getJSONObject("id").getString("videoId"));

            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
            Assert.assertTrue(thumbnails.length() > 0, "Thumbnail URLs not found for video: " + video.getJSONObject("id").getString("videoId"));
        }
        logger.info("searchCatVideos test completed successfully.");
    }
}
