package com.solvd.api.youtube;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

@Endpoint(url = "${base_url}", methodType = HttpMethodType.GET)
@ResponseTemplatePath(path = "youtube/_get/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class JavaBeginnersVideosMethod extends AbstractApiMethodV2 {
    private static final Logger logger = LogManager.getLogger(JavaBeginnersVideosMethod.class);

    public JavaBeginnersVideosMethod() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
        addUrlParameter("part", "snippet");
        addUrlParameter("q", "java beginners");
        addUrlParameter("type", "video");
        addUrlParameter("key", Configuration.getRequired("youtube.api.key"));
    }

    public Response getVideoComments(String videoId) {
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

    public void validateCommentsResponse(Response commentsResponse) {
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
