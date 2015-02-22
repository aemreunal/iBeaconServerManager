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
import com.aemreunal.view.ResponsePanel;

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
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new CreateRegionPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Create", commonPanel);
    }

    private void addGetRegionPanel() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new GetRegionPanel(responsePanel), responsePanel);
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

    private void addDeleteRegionPanel() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new DeleteRegionPanel(responsePanel), responsePanel);
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

    private static void parseRegionJson(JSONObject region, int index, String[][] regionTable) {
        regionTable[index][0] = region.get("regionId").toString();
        regionTable[index][1] = region.get("name").toString();
        regionTable[index][2] = region.get("description").toString();
        regionTable[index][3] = getSubObjectID("scenario", region.optJSONObject("scenario"));
        regionTable[index][4] = new Date(Long.parseLong(region.get("creationDate").toString())).toString();
    }
}