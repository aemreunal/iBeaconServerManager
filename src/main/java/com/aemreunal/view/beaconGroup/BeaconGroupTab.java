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
//        addGetBeaconGroupPanel();
//        addGetMembersPanel();
//        addAddingMemberPanel();
//        addRemovingMemberPanel();
        addDeleteBeaconGroupPanel();
    }

    private void addCreateBeaconGroupPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new CreateBeaconGroupPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    private void addDeleteBeaconGroupPanel() {
        CommonPanel commonPanel = new CommonPanel();
        ResponsePanel responsePanel = new ResponsePanel();
        commonPanel.setTopPanel(new DeleteBeaconGroupPanel(responsePanel));
        commonPanel.setBottomPanel(responsePanel);
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
