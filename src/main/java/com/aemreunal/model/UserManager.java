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

public class UserManager extends RestManager {
    public static HttpResponse<JsonNode> registerUser(String username, String password) {
        HttpRequest request = Unirest.post(PrefsManager.getServerAddress() + "/register")
                                     .header("Content-Type", "application/json")
                                     .body(getUserRegisterJson(username, password))
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    private static String getUserRegisterJson(String username, String password) {
        return "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
    }

    public static HttpResponse<JsonNode> getUser(String username) {
        HttpRequest request = Unirest.get(PrefsManager.getServerAddress() + "/human/" + username);
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> deleteUser(String username) {
        HttpRequest request = Unirest.delete(PrefsManager.getServerAddress() + "/human/" + username + "?confirm=yes")
                                     .getHttpRequest();
        return performJsonRequest(request);
    }
}
