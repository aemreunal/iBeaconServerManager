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
import com.mashape.unirest.http.exceptions.UnirestException;

public class RegisterManager {
    public static HttpResponse<JsonNode> registerUser(String username, String password) {
        System.out.println("Registering user with username: " + username + ", password: " + password);
        HttpResponse<JsonNode> jsonResponse;
        try {
            jsonResponse = Unirest.post("http://localhost:8080/iBeacon/human/register")
                                  .header("accept", "application/json")
                                  .header("Content-Type", "application/json")
                                  .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                                  .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new NullPointerException("Empty response!");
        }
        return jsonResponse;
    }
}
