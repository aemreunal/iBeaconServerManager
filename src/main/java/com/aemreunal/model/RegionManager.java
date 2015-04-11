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
import java.io.InputStream;
import java.net.URLConnection;
import org.apache.http.entity.ContentType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

public class RegionManager extends RestManager {

    public static HttpResponse<JsonNode> createRegion(String projectId, String name, String description, File imageFile) {
        String imageContentType = URLConnection.guessContentTypeFromName(imageFile.getName());
//        String imageContentType = Files.probeContentType(imageFile.toPath());
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions")
                                     .field("region", getRegionCreateJson(name, description), ContentType.APPLICATION_JSON.getMimeType())
                                     .field("image", imageFile, imageContentType)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    private static String getRegionCreateJson(String name, String description) {
        return "{\"name\":\"" + name + "\",\"description\":\"" + description + "\"}";
    }

    public static HttpResponse<JsonNode> getAllRegions(String name, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions?name=" + name)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> getRegion(String regionId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> getRegionMembers(String regionId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId + "/beacons")
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<InputStream> getRegionMapImage(String projectId, String regionId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId + "/mapimage")
                                     .header("Accept", ContentType.APPLICATION_OCTET_STREAM.getMimeType())
                                     .getHttpRequest();
        return performBinaryRequest(request);
    }

    public static HttpResponse<JsonNode> deleteRegion(String regionId, String projectId) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId + "?confirm=yes")
                                     .getHttpRequest();
        return performJsonRequest(request);
    }
}
