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

public class ScenarioManager extends RestManager {

    public static HttpResponse<JsonNode> createScenario(String name, String description, String shortMsg, String longMsg, String url, String projectId) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios")
                                     .body(getScenarioCreateJson(name, description, shortMsg, longMsg, url))
                                     .getHttpRequest();
        return performRequest(request);
    }

    private static String getScenarioCreateJson(String name, String description, String shortMsg, String longMsg, String url) {
        return new JSONObject().put("name", name)
                               .put("description", description)
                               .put("messageShort", shortMsg)
                               .put("messageLong", longMsg)
                               .put("url", url).toString();
    }

    public static HttpResponse<JsonNode> getAllScenarios(String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios")
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> getScenario(String scenarioId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios/" + scenarioId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> getScenarioMemberBeacons(String scenarioId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios/" + scenarioId + "/beacons")
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> getScenarioMemberBeaconGroups(String scenarioId, String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios/" + scenarioId + "/beacongroups")
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> addBeacon(String beaconId, String scenarioId, String projectId) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios/" + scenarioId + "/addbeacon?beaconId=" + beaconId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> addBeaconGroup(String beaconGroupId, String scenarioId, String projectId) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios/" + scenarioId + "/addbeacongroup?beaconGroupId=" + beaconGroupId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> removeBeacon(String beaconId, String scenarioId, String projectId) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios/" + scenarioId + "/removebeacon?beaconId=" + beaconId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> removeBeaconGroup(String beaconGroupId, String scenarioId, String projectId) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios/" + scenarioId + "/removebeacongroup?beaconGroupId=" + beaconGroupId)
                                     .getHttpRequest();
        return performRequest(request);
    }

    public static HttpResponse<JsonNode> deleteScenario(String scenarioId, String projectId) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/scenarios/" + scenarioId + "?confirm=yes")
                                     .getHttpRequest();
        return performRequest(request);
    }
}
