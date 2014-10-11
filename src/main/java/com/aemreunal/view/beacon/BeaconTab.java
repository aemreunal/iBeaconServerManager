package com.aemreunal.view.beacon;

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

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import com.aemreunal.view.CommonPanel;
import com.aemreunal.view.CommonTab;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;

public class BeaconTab extends CommonTab {

    @Override
    protected void addPanels() {
        addCreateBeaconPanel();
        addGetBeaconPanel();
        addDeleteBeaconPanel();
    }

    private void addCreateBeaconPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new CreateBeaconPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    private void addGetBeaconPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new GetBeaconPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Get", commonPanel);
    }

    private void addDeleteBeaconPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new DeleteBeaconPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Delete", commonPanel);
    }

    public static String[][] convertBeaconJsonToTable(JSONObject beacon) {
        String[][] beaconTable = new String[1][ItemTable.BEACONS_TABLE_COL_NAMES.length];
        parseBeaconJson(beacon, 0, beaconTable);
        return beaconTable;
    }

    public static String[][] convertBeaconsJsonToTable(JSONArray beacons) {
        String[][] beaconTable = new String[beacons.length()][ItemTable.BEACONS_TABLE_COL_NAMES.length];
        for (int i = 0; i < beacons.length(); i++) {
            JSONObject beacon = beacons.getJSONObject(i);
            parseBeaconJson(beacon, i, beaconTable);
        }
        return beaconTable;
    }

    /*
     * 0  "Beacon ID",     *   "beaconId" : 2,
     * 1  "UUID",          *   "uuid" : "E686DF2A-C0E0-3777-819A-0526356BD1B6",
     * 2  "Major",         *   "major" : "6",
     * 3  "Minor",         *   "minor" : "799",
     * 4  "Description",   *   "description" : "Et iusto nihil in. Veniam provident eaque labore ratione aperiam.",
     * 5  "Group ID",      *   "group" : null
     * 6  "Scenario ID",   *   "scenario" : null,
     * 7  "Creation Date"  *   "creationDate" : 1413014830179,
    */
    private static void parseBeaconJson(JSONObject beacon, int index, String[][] beaconTable) {
        beaconTable[index][0] = beacon.get("beaconId").toString();
        beaconTable[index][1] = beacon.get("uuid").toString();
        beaconTable[index][2] = beacon.get("major").toString();
        beaconTable[index][3] = beacon.get("minor").toString();
        beaconTable[index][4] = beacon.get("description").toString();
        String groupID = getObjectID("beaconGroup", beacon.get("group"));
        beaconTable[index][5] = groupID;
        if (groupID.equals("-")) {
            beaconTable[index][6] = getObjectID("scenario", beacon.get("scenario"));
        } else {
            String groupScenarioID = getObjectID("scenario", beacon.getJSONObject("group").get("scenario"));
            beaconTable[index][6] = groupScenarioID + " (via group)";
        }
        beaconTable[index][7] = new Date(Long.parseLong(beacon.get("creationDate").toString())).toString();
    }

    private static String getObjectID(String keyName, Object object) {
        if (object == null || object.toString().equals("null")) {
            return "-";
        } else {
            return ((JSONObject) object).get(keyName + "Id").toString();
        }
    }
}
