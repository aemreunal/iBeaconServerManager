package com.aemreunal.view;

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
import javax.swing.table.DefaultTableModel;

public class ItemTable extends JTable {
    public static final String[] USER_TABLE_COL_NAMES        = { "User ID", "Username" };
    public static final String[] PROJECT_TABLE_COL_NAMES     = { "Project ID", "Name", "Description", "Creation Date" };
    public static final String[] BEACONGROUP_TABLE_COL_NAMES = { "Group ID", "Name", "Description", "Scenario", "Creation Date" };
    public static final String[] BEACONS_TABLE_COL_NAMES     = { "Beacon ID", "UUID", "Major", "Minor",
            "Description", "Group ID", "Scenario ID", "Creation Date" };
    public static final String[] API_QUERY_TABLE_COL_NAMES   = { "Short Msg.", "Long Msg.", "URL" };


    public ItemTable(String[][] tableData, String[] columnNames) {
        super(new ItemTableModel(tableData, columnNames));
    }
}

class ItemTableModel extends DefaultTableModel {
    public ItemTableModel(String[][] tableData, String[] columnNames) {
        super(tableData, columnNames);
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

