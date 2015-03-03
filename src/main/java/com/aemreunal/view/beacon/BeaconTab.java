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
import com.aemreunal.view.TableResponsePanel;

public class BeaconTab extends CommonTab {

    @Override
    protected void addPanels() {
        addCreateBeaconPanel();
        addGetBeaconPanel();
        addDeleteBeaconPanel();
    }

    private void addCreateBeaconPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new CreateBeaconPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    private void addGetBeaconPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetBeaconPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Get", commonPanel);
    }

    private void addDeleteBeaconPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new DeleteBeaconPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Delete", commonPanel);
    }

    public static String[][] convertBeaconJsonToTable(JSONObject beacon) {
        String[][] beaconTable = new String[1][ItemTable.BEACONS_TABLE_COL_NAMES.length];
        parseBeaconJson(beacon, 0, beaconTable);
        return beaconTable;
    }

    public static String[][] convertBeaconJsonToTable(JSONArray beacons) {
        String[][] beaconTable = new String[beacons.length()][ItemTable.BEACONS_TABLE_COL_NAMES.length];
        for (int i = 0; i < beacons.length(); i++) {
            JSONObject beacon = beacons.getJSONObject(i);
            parseBeaconJson(beacon, i, beaconTable);
        }
        return beaconTable;
    }

    private static void parseBeaconJson(JSONObject beacon, int index, String[][] beaconTable) {
        beaconTable[index][0] = beacon.get("beaconId").toString();
        beaconTable[index][1] = beacon.get("uuid").toString();
        beaconTable[index][2] = beacon.get("major").toString();
        beaconTable[index][3] = beacon.get("minor").toString();
        beaconTable[index][4] = beacon.get("description").toString();
        beaconTable[index][5] = getSubObjectID("scenario", beacon.optJSONObject("scenario"));
        beaconTable[index][6] = new Date(Long.parseLong(beacon.get("creationDate").toString())).toString();
    }
}
