package com.solvd.api.youtube;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Endpoint(url = "${base_url}/commentThreads", methodType = HttpMethodType.GET)
@ResponseTemplatePath(path = "api/youtube/_get/comments_rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetVideoCommentsMethod extends AbstractApiMethodV2 {
    private static final Logger logger = LogManager.getLogger(GetVideoCommentsMethod.class);

    public GetVideoCommentsMethod(String videoId) {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
        addUrlParameter("part", "snippet,replies");
        addUrlParameter("key", Configuration.getRequired("youtube.api.key"));
        addUrlParameter("videoId", videoId);
        addUrlParameter("maxResults", "3");
    }
}
