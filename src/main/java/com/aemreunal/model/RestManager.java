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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import javax.swing.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

public class RestManager {
    protected static HttpResponse<JsonNode> performJsonRequest(HttpRequest request) {
        authenticateRequest(request);
        HttpResponse<JsonNode> jsonResponse = makeJsonRequest(request);
        checkJsonResponse(jsonResponse);
        return jsonResponse;
    }

    protected static HttpResponse<InputStream> performBinaryRequest(HttpRequest request) {
        authenticateRequest(request);
        HttpResponse<InputStream> binaryResponse = makeBinaryRequest(request);
        try {
            checkBinaryResponse(binaryResponse);
        } catch (IOException e) {
            // Ignore exception
        }
        return binaryResponse;
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
            System.err.println("A Unirest exception occurred!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    private static HttpResponse<InputStream> makeBinaryRequest(HttpRequest request) {
        try {
            return request.asBinary();
        } catch (UnirestException e) {
            JOptionPane.showMessageDialog(null, "Request failed. Please check the server and account settings.");
            System.err.println("A Unirest exception occurred!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    private static void checkJsonResponse(HttpResponse<JsonNode> jsonResponse) {
        if (jsonResponse != null && jsonResponse.getStatus() >= 400 && jsonResponse.getStatus() < 500) {
            JOptionPane.showMessageDialog(null, getErrorMessage(jsonResponse.getBody().getObject()), "An error occurred!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void checkBinaryResponse(HttpResponse<InputStream> binaryResponse) throws IOException {
        if (binaryResponse != null && binaryResponse.getStatus() >= 400 && binaryResponse.getStatus() < 500) {
            StringWriter writer = new StringWriter();
            IOUtils.copy(binaryResponse.getRawBody(), writer, "UTF-8");

            String errorMessage = getErrorMessage(new JSONObject(writer.toString()));
            JOptionPane.showMessageDialog(null, errorMessage, "An error occurred!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String getErrorMessage(JSONObject object) {
        return "An error occurred with the last request.\nError message: \"" + object.getString("error") + "\"";
    }
}

