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

import java.util.prefs.Preferences;
import com.aemreunal.view.ManagerWindow;
import com.mashape.unirest.http.Unirest;

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
    private static Preferences preferences;

    public static void main(String[] args) {
        new iBeaconServerManager();
    }

    public iBeaconServerManager() {
        preferences = Preferences.userRoot().node(this.getClass().getName());
        Unirest.setDefaultHeader("Content-Type", "application/json");
        Unirest.setDefaultHeader("Accept", "application/json");
        new ManagerWindow();
    }

    public static Preferences getPreferences() {
        return preferences;
    }
}
