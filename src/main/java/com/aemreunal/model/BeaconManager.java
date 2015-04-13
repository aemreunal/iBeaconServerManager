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

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import javax.swing.*;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.body.MultipartBody;

public class BeaconManager extends RestManager {

    private static String beaconUrl(String projectId, String regionId) {
        return PrefsManager.getServerAddress() + "/human/" + PrefsManager.getUsername() + "/projects/" + projectId + "/regions/" + regionId + "/beacons";
    }

    public static HttpResponse<JsonNode> createBeacon(String projectId, String regionId, String uuid, String major, String minor, String description, String displayName, String xCoor, String yCoor, boolean isDesignated) {
        HttpRequest request = getBeaconCreateBody(projectId, regionId, uuid, major, minor, description, displayName, xCoor, yCoor, isDesignated)
                .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> createBeacon(String projectId, String regionId, String uuid, String major, String minor, String description, String displayName, String xCoor, String yCoor, boolean isDesignated, String locationInfoText) {
        File textFile = getBeaconLocationInfoTextFile(locationInfoText);
        if (textFile == null) {
            JOptionPane.showMessageDialog(null, "Error creating text file! The request won't be performed.", "File Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        HttpRequest request = getBeaconCreateBody(projectId, regionId, uuid, major, minor, description, displayName, xCoor, yCoor, isDesignated)
                .field("info", textFile)
                .getHttpRequest();
        return performJsonRequest(request);
    }

    private static MultipartBody getBeaconCreateBody(String projectId, String regionId, String uuid, String major, String minor, String description, String displayName, String xCoor, String yCoor, boolean isDesignated) {
        return Unirest.post(beaconUrl(projectId, regionId))
                      .field("beacon", getBeaconCreateJson(uuid, major, minor, description, displayName, xCoor, yCoor, isDesignated), ContentType.APPLICATION_JSON.getMimeType(), true);
    }

    public static HttpResponse<JsonNode> getAllBeacons(String projectId, String regionId, String uuid, String major, String minor) {
        HttpRequest request = Unirest.get(beaconUrl(projectId, regionId) + "?uuid=" + uuid + "&major=" + major + "&minor=" + minor)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> getBeacon(String beaconId, String projectId, String regionId) {
        HttpRequest request = Unirest.get(beaconUrl(projectId, regionId) + "/" + beaconId)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> connectBeacon(String projectId, String beaconOneId, String beaconTwoId, String regionOneId, String regionTwoId, File imageFile) {
        String imageContentType = URLConnection.guessContentTypeFromName(imageFile.getName());
        HttpRequest request = Unirest.post(beaconUrl(projectId, regionOneId) + "/" + beaconOneId + "/connection?beacon2id=" + beaconTwoId + "&region2id=" + regionTwoId)
                                     .field("image", imageFile, imageContentType)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> disconnectBeacon(String projectId, String beaconOneId, String beaconTwoId, String regionOneId, String regionTwoId) {
        HttpRequest request = Unirest.delete(beaconUrl(projectId, regionOneId) + "/" + beaconOneId + "/connection?beacon2id=" + beaconTwoId + "&region2id=" + regionTwoId)
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static HttpResponse<JsonNode> deleteBeacon(String beaconId, String projectId, String regionId) {
        HttpRequest request = Unirest.delete(beaconUrl(projectId, regionId) + "/" + beaconId + "?confirm=yes")
                                     .getHttpRequest();
        return performJsonRequest(request);
    }

    public static String getBeaconCreateJson(String uuid, String major, String minor, String description, String displayName, String xCoor, String yCoor, boolean isDesignated) {
        return new JSONObject().put("uuid", uuid)
                               .put("major", major)
                               .put("minor", minor)
                               .put("xCoordinate", xCoor)
                               .put("yCoordinate", yCoor)
                               .put("designated", isDesignated)
                               .put("description", description)
                               .put("displayName", displayName)
                               .toString();
    }

    public static File getBeaconLocationInfoTextFile(String locationInfoText) {
        try {
            File tempFile = File.createTempFile("info", "txt");
            Files.write(tempFile.toPath(), locationInfoText.getBytes());
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
