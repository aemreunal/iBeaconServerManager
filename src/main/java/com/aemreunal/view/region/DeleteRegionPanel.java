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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.aemreunal.model.RegionManager;
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class DeleteRegionPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField regionIdField;
    private JButton    deleteButton;

    public DeleteRegionPanel(final TableResponsePanel tableResponsePanel) {
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        projectIdField = new JTextField(5);
        regionIdField = new JTextField(5);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteBeaconActionListener(tableResponsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Project ID:"));
        this.add(projectIdField);
        this.add(new JLabel("Region ID:"));
        this.add(regionIdField);
        this.add(deleteButton);
    }

    private class DeleteBeaconActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public DeleteBeaconActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String regionId = regionIdField.getText().trim();
            String projectId = projectIdField.getText().trim();
            if (!regionId.isEmpty() && !projectId.isEmpty()) {
                HttpResponse<JsonNode> response = RegionManager.deleteRegion(regionId, projectId);
                tableResponsePanel.showResponseCode(response.getStatus());
                if (response.getStatus() == 200) {
                    String[][] regionResponse = RegionTab.convertRegionJsonToTable(response.getBody().getObject());
                    tableResponsePanel.showResponseTable(ItemTable.REGIONS_TABLE_COL_NAMES, regionResponse);
                }
            }
        }
    }

}
