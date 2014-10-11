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

public class BeaconManager extends RestManager {

    public static HttpResponse<JsonNode> createBeacon(String uuid, String major, String minor, String description, String projectId) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacons")
                                     .body(getBeaconCreateJson(uuid, major, minor, description))
                                     .getHttpRequest();
        return performRequest(request);
    }

//    public static HttpResponse<JsonNode> getAllBeacons(String beaconId, String projectId) {
//        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacons/" + beaconId)
//                                     .getHttpRequest();
//        return performRequest(request);
//    }

    public static HttpResponse<JsonNode> getBeacon(String beaconId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacons/" + beaconId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static String getBeaconCreateJson(String uuid, String major, String minor, String description) {
        return new JSONObject().put("uuid", uuid)
                               .put("major", major)
                               .put("minor", minor)
                               .put("description", description)
                               .toString();
    }
}
