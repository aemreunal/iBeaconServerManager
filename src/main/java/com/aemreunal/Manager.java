package com.aemreunal;

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
import com.aemreunal.view.ManagerWindow;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/*
 * Unirest: http://unirest.io/java.html
 *
 * HttpResponse<JsonNode> jsonResponse = Unirest.post("http://httpbin.org/post")
 *                                              .header("accept", "application/json")
 *                                              .field("parameter", "value")
 *                                              .field("foo", "bar")
 *                                              .asJson();
 *
 */

public class Manager {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        new ManagerWindow();

//        testRequest();
    }

    private static void testRequest() {
        HttpResponse<JsonNode> jsonResponse;
        try {
            jsonResponse = Unirest.get("http://localhost:8080/iBeacon/human/testuser12/projects/1")
                                  .header("accept", "application/json")
                                  .asJson();
            System.out.println(jsonResponse.getBody().getObject().toString(2));
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        try {
            Unirest.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
