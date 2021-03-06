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

public class ProjectManager extends RestManager {

    public static HttpResponse<JsonNode> createProject(String name, String description) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects")
                                     .header("Content-Type", "application/json")
                                     .body(getProjectCreateJson(name, description))
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    private static String getProjectCreateJson(String name, String description) {
        return "{\"name\":\"" + name + "\",\"description\":\"" + description + "\"}";
    }

    public static HttpResponse<JsonNode> getAllProjects(String projectName) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects?name=" + projectName)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> getProject(String projectId) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> deleteProject(String projectId) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "?confirm=yes")
                                     .getHttpRequest();
        return performJsonRequest(request);
    }
}
