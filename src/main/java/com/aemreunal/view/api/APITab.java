package com.aemreunal.view.api;

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

import org.json.JSONObject;
import com.aemreunal.view.CommonPanel;
import com.aemreunal.view.CommonTab;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;

public class APITab extends CommonTab {

    @Override
    protected void addPanels() {
        addQueryBeaconPanel();
    }

    private void addQueryBeaconPanel() {
        ResponsePanel responsePanel = new ResponsePanel();
        CommonPanel commonPanel = new CommonPanel(new QueryBeaconPanel(responsePanel), responsePanel);
        this.tabbedPane.addTab("Query", commonPanel);
    }
    
    public static String[][] convertQueryJsonToTable(JSONObject project) {
        String[] queryTable = new String[ItemTable.API_QUERY_TABLE_COL_NAMES.length];
        queryTable[0] = project.get("short").toString();
        queryTable[1] = project.get("long").toString();
        queryTable[2] = project.get("url").toString();
        return new String[][] { queryTable };
    }
}
