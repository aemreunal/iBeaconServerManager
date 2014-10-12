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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

public class BeaconGroupManager extends RestManager {

    public static HttpResponse<JsonNode> createGroup(String name, String description, String projectId) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacongroups")
                                     .body(getGroupCreateJson(name, description))
                                     .getHttpRequest();
        return performRequest(request);
    }

    private static String getGroupCreateJson(String name, String description) {
        return "{\"name\":\"" + name + "\",\"description\":\"" + description + "\"}";
    }

    public static HttpResponse<JsonNode> getAllGroups(String name, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacongroups?name=" + name)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> getGroup(String beaconGroupId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacongroups/" + beaconGroupId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> getGroupMembers(String beaconGroupId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacongroups/" + beaconGroupId + "/members")
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> addMember(String beaconId, String beaconGroupId, String projectId) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacongroups/" + beaconGroupId + "/addmember?beaconId=" + beaconId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> removeMember(String beaconId, String beaconGroupId, String projectId) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacongroups/" + beaconGroupId + "/removemember?beaconId=" + beaconId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> deleteGroup(String beaconGroupId, String projectId) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/iBeacon/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/beacongroups/" + beaconGroupId + "?confirm=yes")
                                     .getHttpRequest();
        return performRequest(request);
    }
}
