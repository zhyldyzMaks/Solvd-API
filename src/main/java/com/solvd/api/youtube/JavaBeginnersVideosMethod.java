package com.solvd.api.youtube;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}", methodType = HttpMethodType.GET)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class JavaBeginnersVideosMethod extends AbstractApiMethodV2 {

    public JavaBeginnersVideosMethod() {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
        addUrlParameter("part", "snippet");
        addUrlParameter("q", "java beginners");
        addUrlParameter("type", "video");
        addUrlParameter("key", Configuration.getRequired("youtube.api.key"));
    }
}
