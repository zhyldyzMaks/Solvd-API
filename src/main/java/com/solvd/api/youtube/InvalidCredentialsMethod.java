package com.solvd.api.youtube;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}/search", methodType = HttpMethodType.GET)
@SuccessfulHttpStatus(status = HttpResponseStatusType.BAD_REQUEST_400)
public class InvalidCredentialsMethod extends AbstractApiMethodV2 {

    public InvalidCredentialsMethod() {
        replaceUrlPlaceholder("base_url",Configuration.getRequired("api_url"));
        addUrlParameter("type", "video");
        addUrlParameter("key", "Invalid-API-KEY");
    }
}
