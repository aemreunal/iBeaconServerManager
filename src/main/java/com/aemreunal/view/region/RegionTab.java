package com.aemreunal.view.region;

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

public class RegionTab extends CommonTab {

    @Override
    protected void addPanels() {
        addCreateRegionPanel();
        addGetRegionPanel();
        addGetMembersPanel();
        addModifyMemberPanel();
        addDeleteRegionPanel();
    }

    private void addCreateRegionPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new CreateRegionPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    private void addGetRegionPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetRegionPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Get", commonPanel);
    }

    private void addGetMembersPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetMembersPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Members", commonPanel);
    }

    private void addModifyMemberPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new ModifyMembersPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Modify", commonPanel);
    }

    private void addDeleteRegionPanel() {
        TableResponsePanel tableResponsePanel = new TableResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new DeleteRegionPanel(tableResponsePanel), tableResponsePanel);
        this.tabbedPane.addTab("Delete", commonPanel);
    }

    public static String[][] convertRegionJsonToTable(JSONObject region) {
        String[][] regionTable = new String[1][ItemTable.REGIONS_TABLE_COL_NAMES.length];
        parseRegionJson(region, 0, regionTable);
        return regionTable;
    }

    public static String[][] convertRegionJsonToTable(JSONArray regions) {
        String[][] regionTable = new String[regions.length()][ItemTable.REGIONS_TABLE_COL_NAMES.length];
        for (int i = 0; i < regions.length(); i++) {
            JSONObject region = regions.getJSONObject(i);
            parseRegionJson(region, i, regionTable);
        }
        return regionTable;
    }

    private static void parseRegionJson(JSONObject regionJson, int index, String[][] regionTable) {
        regionTable[index][0] = regionJson.get("regionId").toString();
        regionTable[index][1] = regionJson.get("name").toString();
        regionTable[index][2] = regionJson.get("description").toString();
        regionTable[index][3] = getSubObjectID("scenario", regionJson.optJSONObject("scenario"));
        regionTable[index][4] = regionJson.get("mapImageIsSet").toString();
        regionTable[index][5] = new Date(Long.parseLong(regionJson.get("lastUpdatedDate").toString())).toString();
        regionTable[index][6] = new Date(Long.parseLong(regionJson.get("creationDate").toString())).toString();
    }
}
