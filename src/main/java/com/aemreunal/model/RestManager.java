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
        try {
            HttpResponse<JsonNode> jsonResponse = request.asJson();
            if (jsonResponse.getCode() >= 400 && jsonResponse.getCode() < 500) {
                JOptionPane.showMessageDialog(null, getErrorMessage(jsonResponse.getBody().getObject()), "An error ocurred!", JOptionPane.ERROR_MESSAGE);
            }
            return jsonResponse;
        } catch (UnirestException e) {
            System.err.println("A Unirest exception ocurred!");
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new NullPointerException();
        }
    }

    private static String getErrorMessage(JSONObject object) {
        return "An error ocurred with the last request.\nThe cause of error: " + object.getString("reason") + "\nError message: " + object.getString("error");
    }
}
