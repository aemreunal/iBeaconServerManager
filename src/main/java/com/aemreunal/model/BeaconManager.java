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

    private static String beaconUrl(String projectId, String regionId) {
        return PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId + "/beacons";
    }

    public static HttpResponse<JsonNode> createBeacon(String projectId, String regionId, String uuid, String major, String minor, String description, String xCoor, String yCoor, boolean isDesignated) {
        HttpRequest request = Unirest.post(beaconUrl(projectId, regionId))
                                     .header("Content-Type", "application/json")
                                     .body(getBeaconCreateJson(uuid, major, minor, description, xCoor, yCoor, isDesignated))
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> getAllBeacons(String projectId, String regionId, String uuid, String major, String minor) {
        HttpRequest request = Unirest.get(beaconUrl(projectId, regionId) + "?uuid=" + uuid + "&major=" + major + "&minor=" + minor)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> getBeacon(String beaconId, String projectId, String regionId) {
        HttpRequest request = Unirest.get(beaconUrl(projectId, regionId) + "/" + beaconId)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> deleteBeacon(String beaconId, String projectId, String regionId) {
        HttpRequest request = Unirest.delete(beaconUrl(projectId, regionId) + "/" + beaconId + "?confirm=yes")
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static String getBeaconCreateJson(String uuid, String major, String minor, String description, String xCoor, String yCoor, boolean isDesignated) {
        return new JSONObject().put("uuid", uuid)
                               .put("major", major)
                               .put("minor", minor)
                               .put("xCoordinate", xCoor)
                               .put("yCoordinate", yCoor)
                               .put("designated", isDesignated)
                               .put("description", description)
                               .toString();
    }
}
