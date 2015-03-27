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
import java.util.LinkedHashSet;
import org.json.JSONArray;
import org.json.JSONObject;
import com.aemreunal.view.CommonPanel;
import com.aemreunal.view.CommonTab;
import com.aemreunal.view.response.image.imageViewer.IVBeacon;
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;

public class BeaconTab extends CommonTab {

    public static final int BEACON_ID     = 0;
    public static final int UUID          = 1;
    public static final int MAJOR         = 2;
    public static final int MINOR         = 3;
    public static final int X_COOR        = 4;
    public static final int Y_COOR        = 5;
    public static final int DESIGNATED    = 6;
    public static final int SCENARIO      = 7;
    public static final int DESCRIPTION   = 8;
    public static final int CREATION_DATE = 9;

    @Override
    protected void addPanels() {
        addCreateBeaconPanel();
        addGetBeaconPanel();
        addConnectBeaconPanel();
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

    private void addConnectBeaconPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new ConnectBeaconPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Connect", commonPanel);
    }

    private void addDeleteBeaconPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new DeleteBeaconPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Delete", commonPanel);
    }

    public static String[][] convertBeaconJsonToTable(JSONObject beacon) {
        String[][] beaconTable = new String[1][ItemTable.BEACONS_TABLE_COL_NAMES.length];
        parseBeaconJsonToArray(beacon, 0, beaconTable);
        return beaconTable;
    }

    public static String[][] convertBeaconJsonToTable(JSONArray beacons) {
        String[][] beaconTable = new String[beacons.length()][ItemTable.BEACONS_TABLE_COL_NAMES.length];
        for (int i = 0; i < beacons.length(); i++) {
            JSONObject beacon = beacons.getJSONObject(i);
            parseBeaconJsonToArray(beacon, i, beaconTable);
        }
        return beaconTable;
    }

    public static LinkedHashSet<IVBeacon> convertBeaconJsonToIvBeacon(JSONArray beacons) {
        LinkedHashSet<IVBeacon> ivBeacons = new LinkedHashSet<>();
        for (int i = 0; i < beacons.length(); i++) {
            JSONObject beacon = beacons.getJSONObject(i);
            ivBeacons.add(parseBeaconJsonToIvBeacon(beacon));
        }
        return ivBeacons;
    }

    private static void parseBeaconJsonToArray(JSONObject beacon, int index, String[][] beaconTable) {
        beaconTable[index][BEACON_ID] = beacon.get("beaconId").toString();
        beaconTable[index][UUID] = beacon.get("uuid").toString();
        beaconTable[index][MAJOR] = beacon.get("major").toString();
        beaconTable[index][MINOR] = beacon.get("minor").toString();
        beaconTable[index][X_COOR] = beacon.get("xCoordinate").toString();
        beaconTable[index][Y_COOR] = beacon.get("yCoordinate").toString();
        beaconTable[index][DESIGNATED] = beacon.get("designated").toString();
        beaconTable[index][DESCRIPTION] = beacon.get("description").toString();
        beaconTable[index][SCENARIO] = getSubObjectID("scenario", beacon.optJSONObject("scenario"));
        beaconTable[index][CREATION_DATE] = new Date(Long.parseLong(beacon.get("creationDate").toString())).toString();
    }

    private static IVBeacon parseBeaconJsonToIvBeacon(JSONObject beacon) {
        long beaconId = Long.parseLong(beacon.get("beaconId").toString());
        int xCoordinate = Integer.parseInt(beacon.get("xCoordinate").toString());
        int yCoordinate = Integer.parseInt(beacon.get("yCoordinate").toString());
        boolean isDesignated = Boolean.parseBoolean(beacon.get("designated").toString());

        return new IVBeacon(beaconId, xCoordinate, yCoordinate, isDesignated);
    }
}
