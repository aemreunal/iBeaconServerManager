package com.aemreunal.view.beaconGroup;

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
import com.aemreunal.model.BeaconGroupManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.aemreunal.view.beacon.BeaconTab;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetMembersPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField beaconGroupIdField;
    private JButton    getButton;

    public GetMembersPanel(ResponsePanel responsePanel) {
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        projectIdField.addActionListener(new GetActionListener(responsePanel));
        beaconGroupIdField = new JTextField(5);
        beaconGroupIdField.addActionListener(new GetActionListener(responsePanel));
        getButton = new JButton("Get members");
        getButton.addActionListener(new GetActionListener(responsePanel));
    }

    private void addComponents() {
        add(new JLabel("Project ID:"));
        add(projectIdField);
        add(new JLabel("Beacon group ID:"));
        add(beaconGroupIdField);
        add(getButton);
    }

    private class GetActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public GetActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = projectIdField.getText().trim();
            String beaconGroupId = beaconGroupIdField.getText().trim();
            if (projectId.isEmpty() || beaconGroupId.isEmpty()) {
                return;
            }
            HttpResponse<JsonNode> response = BeaconGroupManager.getGroupMembers(beaconGroupId, projectId);
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                String[][] beaconResponse;
                JsonNode responseBody = response.getBody();
                beaconResponse = BeaconTab.convertBeaconsJsonToTable(responseBody.getArray());
                responsePanel.showResponseTable(ItemTable.BEACONS_TABLE_COL_NAMES, beaconResponse);
            }
        }
    }
}
