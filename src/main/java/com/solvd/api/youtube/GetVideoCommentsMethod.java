package com.solvd.api.youtube;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}/commentThreads", methodType = HttpMethodType.GET)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetVideoCommentsMethod extends AbstractApiMethodV2 {

    public GetVideoCommentsMethod(String videoId) {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
        addUrlParameter("part", "snippet,replies");
        addUrlParameter("key", Configuration.getRequired("youtube.api.key"));
        addUrlParameter("videoId", videoId);
        addUrlParameter("maxResults", "3");
    }
}
