package com.aemreunal.view.beaconGroup;

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

public class BeaconGroupTab extends CommonTab {

    @Override
    protected void addPanels() {
        addCreateBeaconGroupPanel();
        addGetBeaconGroupPanel();
        addGetMembersPanel();
        addModifyMemberPanel();
        addDeleteBeaconGroupPanel();
    }

    private void addCreateBeaconGroupPanel() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new CreateBeaconGroupPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    private void addGetBeaconGroupPanel() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetBeaconGroupPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Get", commonPanel);
    }

    private void addGetMembersPanel() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetMembersPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Members", commonPanel);
    }

    private void addModifyMemberPanel() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new ModifyMembersPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Modify", commonPanel);
    }

    private void addDeleteBeaconGroupPanel() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new DeleteBeaconGroupPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Delete", commonPanel);
    }

    public static String[][] convertBeaconGroupJsonToTable(JSONObject beaconGroup) {
        String[][] beaconGroupTable = new String[1][ItemTable.BEACONGROUPS_TABLE_COL_NAMES.length];
        parseBeaconGroupJson(beaconGroup, 0, beaconGroupTable);
        return beaconGroupTable;
    }

    public static String[][] convertBeaconGroupsJsonToTable(JSONArray beaconGroups) {
        String[][] beaconGroupTable = new String[beaconGroups.length()][ItemTable.BEACONGROUPS_TABLE_COL_NAMES.length];
        for (int i = 0; i < beaconGroups.length(); i++) {
            JSONObject beaconGroup = beaconGroups.getJSONObject(i);
            parseBeaconGroupJson(beaconGroup, i, beaconGroupTable);
        }
        return beaconGroupTable;
    }

    private static void parseBeaconGroupJson(JSONObject beaconGroup, int index, String[][] beaconGroupTable) {
        beaconGroupTable[index][0] = beaconGroup.get("beaconGroupId").toString();
        beaconGroupTable[index][1] = beaconGroup.get("name").toString();
        beaconGroupTable[index][2] = beaconGroup.get("description").toString();
        beaconGroupTable[index][3] = getSubObjectID("scenario", beaconGroup.optJSONObject("scenario"));
        beaconGroupTable[index][4] = new Date(Long.parseLong(beaconGroup.get("creationDate").toString())).toString();
    }
}
