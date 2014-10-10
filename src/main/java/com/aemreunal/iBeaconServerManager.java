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
import java.util.prefs.Preferences;
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

public class iBeaconServerManager {
    private static Preferences   preferences;
    private static ManagerWindow managerWindow;

    public static void main(String[] args) {
        new iBeaconServerManager();
    }

    public iBeaconServerManager() {
        preferences = Preferences.userRoot().node(this.getClass().getName());
//        try {
//            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        } catch (KeyStoreException e) {
//            System.err.println("Unable to get KeyStore instance!");
//            e.printStackTrace();
//        }
        Unirest.setDefaultHeader("Content-Type", "application/json");
        Unirest.setDefaultHeader("Accept", "application/json");
        iBeaconServerManager.managerWindow = new ManagerWindow(preferences);
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    public static ManagerWindow getManagerWindow() {
        return managerWindow;
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
