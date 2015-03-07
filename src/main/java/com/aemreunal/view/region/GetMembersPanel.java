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
import com.aemreunal.view.beacon.BeaconTab;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetMembersPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField regionIdField;
    private JButton    getButton;

    public GetMembersPanel(TableResponsePanel tableResponsePanel) {
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        projectIdField = new JTextField(5);
        projectIdField.addActionListener(new GetActionListener(tableResponsePanel));
        regionIdField = new JTextField(5);
        regionIdField.addActionListener(new GetActionListener(tableResponsePanel));
        getButton = new JButton("Get members");
        getButton.addActionListener(new GetActionListener(tableResponsePanel));
    }

    private void addComponents() {
        add(new JLabel("Project ID:"));
        add(projectIdField);
        add(new JLabel("Region ID:"));
        add(regionIdField);
        add(getButton);
    }

    private class GetActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public GetActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = projectIdField.getText().trim();
            String regionId = regionIdField.getText().trim();
            if (projectId.isEmpty() || regionId.isEmpty()) {
                return;
            }
            HttpResponse<JsonNode> response = RegionManager.getRegionMembers(regionId, projectId);
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] beaconResponse = null;
            if (response.getStatus() == 200) {
                JsonNode responseBody = response.getBody();
                beaconResponse = BeaconTab.convertBeaconJsonToTable(responseBody.getArray());
            }
            tableResponsePanel.showResponseTable(ItemTable.BEACONS_TABLE_COL_NAMES, beaconResponse);
        }
    }
}
