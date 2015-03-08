package com.aemreunal.view.response.table;

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

import javax.swing.*;

public class ItemTable extends JTable {
    public static final String[] USER_TABLE_COL_NAMES      = { "User ID", "Username" };
    public static final String[] PROJECT_TABLE_COL_NAMES   = { "Project ID", "Name", "Description", "Creation Date" };
    public static final String[] REGIONS_TABLE_COL_NAMES   = { "Region ID", "Name", "Description", "Scenario", "Map Image Set?", "Last Updated", "Creation Date" };
    public static final String[] BEACONS_TABLE_COL_NAMES   = { "Beacon ID", "UUID", "Major", "Minor", "Description", "Scenario ID", "Creation Date" };
    public static final String[] SCENARIO_TABLE_COL_NAMES  = { "Scenario ID", "Name", "Description", "Short Msg.", "Long Msg.", "URL", "Creation Date" };
    public static final String[] API_QUERY_TABLE_COL_NAMES = { "Short Msg.", "Long Msg.", "URL" };


    public ItemTable(String[][] tableData, String[] columnNames) {
        super(new ItemTableModel(tableData, columnNames));
    }
}