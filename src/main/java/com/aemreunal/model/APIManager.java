package com.aemreunal.model;

/*
 ***************************
 * Copyright (c) 2014      *
 *                         *
 * This code belongs to:   *
 *                         *
 * @author Ahmet Emre Ünal *
 * S001974                 *
 *                         *
 * emre@aemreunal.com      *
 * emre.unal@ozu.edu.tr    *
 *                         *
 * aemreunal.com           *
 ***************************
 */

import org.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

public class APIManager extends RestManager {

    public static HttpResponse<JsonNode> queryBeacon(String uuid, String major, String minor, String secret) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/iBeacon/robot/querybeacon")
                                     .body(getQueryBeaconJson(uuid, major, minor, secret))
                                     .getHttpRequest();
        return performRequest(request);
    }

    private static String getQueryBeaconJson(String uuid, String major, String minor, String secret) {
        return new JSONObject().put("uuid", uuid)
                               .put("major", major)
                               .put("minor", minor)
                               .put("secret", secret)
                               .toString();
    }
}
