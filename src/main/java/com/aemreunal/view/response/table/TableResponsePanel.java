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

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableModel;

public class TableResponsePanel extends JPanel {
    private JLabel      statusCodeLabel;
    private JScrollPane scrollPane;
    private ItemTable   itemTable;
    private TableModel  tableModel;

    public TableResponsePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initStatusCodeLabel();
        initItemTable();
    }

    private void initStatusCodeLabel() {
        this.statusCodeLabel = new JLabel("Response code: -");
        this.statusCodeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(this.statusCodeLabel, BorderLayout.NORTH);
    }

    private void initItemTable() {
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(this.itemTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void showResponseCode(int httpStatusCode) {
        this.statusCodeLabel.setText("Response code: " + httpStatusCode);
    }

    public void showResponseTable(String[] columnNames, String[][] items) {
        if (items == null) {
            items = new String[0][0];
        }
        this.tableModel = new ItemTableModel(items, columnNames);
        this.itemTable = new ItemTable(tableModel);
        this.itemTable.setRowSorter(new BeaconTableSorter(tableModel));
        this.itemTable.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(this.itemTable);
    }
}
