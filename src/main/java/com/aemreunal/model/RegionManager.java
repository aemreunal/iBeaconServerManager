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

import java.io.File;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

public class RegionManager extends RestManager {

    public static HttpResponse<JsonNode> createRegion(String name, String description, String projectId) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions")
                                     .header("Content-Type", "application/json")
                                     .body(getRegionCreateJson(name, description))
                                     .getHttpRequest();
        return performRequest(request);
    }

    private static String getRegionCreateJson(String name, String description) {
        return "{\"name\":\"" + name + "\",\"description\":\"" + description + "\"}";
    }

    public static HttpResponse<JsonNode> getAllRegions(String name, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions?name=" + name)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> getRegion(String regionId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> getRegionMembers(String regionId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId + "/members")
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> uploadRegionMapImage(String regionId, String projectId) {
        File imageFile = new File("/Users/aemreunal/IntelliJ/iBeacon/DevResources/test1.jpg");
        if (!imageFile.exists()) {
            System.err.println("Unable to read image!");
            return null;
        }
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId + "/uploadmapimage")
                                     .field("mapImage", imageFile, "image/jpeg")
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> deleteRegion(String regionId, String projectId) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId + "?confirm=yes")
                                     .getHttpRequest();
        return performRequest(request);
    }
}
