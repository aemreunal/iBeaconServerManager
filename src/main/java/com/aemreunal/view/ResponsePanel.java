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

import java.awt.*;
import javax.swing.*;

public class ResponsePanel extends JPanel {
    private JLabel      statusCodeLabel;
    private JScrollPane scrollPane;
    private ItemTable   itemTable;

    public ResponsePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane();
        this.statusCodeLabel = new JLabel("Response code: -");
        this.statusCodeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(this.statusCodeLabel, BorderLayout.NORTH);
        this.itemTable = new ItemTable(new String[0][0], new String[0]);
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
        this.itemTable = new ItemTable(items, columnNames);
        this.itemTable.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(this.itemTable);
    }
}
