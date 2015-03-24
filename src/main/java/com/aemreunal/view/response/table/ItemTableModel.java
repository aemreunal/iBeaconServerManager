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

import javax.swing.table.DefaultTableModel;

public class ItemTableModel extends DefaultTableModel {
    public ItemTableModel(String[][] tableData, String[] columnNames) {
        super(tableData, columnNames);
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }


}

