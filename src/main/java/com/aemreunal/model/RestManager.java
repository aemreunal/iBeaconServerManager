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

import javax.swing.*;
import org.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

public class RestManager {
    protected static HttpResponse<JsonNode> performRequest(HttpRequest request) {
        authenticateRequest(request);
        HttpResponse<JsonNode> jsonResponse = makeJsonRequest(request);
        checkResponseForErrorCode(jsonResponse);
        return jsonResponse;
    }

    private static void authenticateRequest(HttpRequest request) {
        if (!isRegisterRequest(request)) {
            request.basicAuth(PrefsManager.getUsername(), PrefsManager.getPassword());
        }
    }

    private static boolean isRegisterRequest(HttpRequest request) {
        return request.getUrl().equals(PrefsManager.getServerAddress() + "/register");
    }

    private static HttpResponse<JsonNode> makeJsonRequest(HttpRequest request) {
        try {
            return request.asJson();
        } catch (UnirestException e) {
            JOptionPane.showMessageDialog(null, "Request failed. Please check the server and account settings.");
            System.err.println("A Unirest exception ocurred!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    private static void checkResponseForErrorCode(HttpResponse<JsonNode> jsonResponse) {
        if (jsonResponse != null && jsonResponse.getStatus() >= 400 && jsonResponse.getStatus() < 500) {
            JOptionPane.showMessageDialog(null, getErrorMessage(jsonResponse.getBody().getObject()), "An error ocurred!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String getErrorMessage(JSONObject object) {
        return "An error ocurred with the last request.\nThe cause of error: " + object.getString("reason") + "\nError message: " + object.getString("error");
    }
}

