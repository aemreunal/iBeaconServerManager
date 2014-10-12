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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.aemreunal.model.BeaconGroupManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetBeaconGroupPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField beaconGroupIdField;
    private JTextField beaconGroupNameField;
    private JButton    getButton;

    public GetBeaconGroupPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        projectIdField.addActionListener(new GetActionListener(responsePanel));
        beaconGroupIdField = new JTextField(5);
        beaconGroupIdField.addActionListener(new GetActionListener(responsePanel));
        beaconGroupNameField = new JTextField(10);
        beaconGroupNameField.addActionListener(new GetActionListener(responsePanel));
        getButton = new JButton("Get");
        getButton.addActionListener(new GetActionListener(responsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.setMinimumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel beaconGroupIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        beaconGroupIdPanel.add(new JLabel("Beacon group ID (leave blank to get all):"));
        beaconGroupIdPanel.add(beaconGroupIdField);
        beaconGroupIdPanel.add(getButton);
        beaconGroupIdPanel.setMinimumSize(beaconGroupIdPanel.getPreferredSize());
        this.add(beaconGroupIdPanel);

        JPanel beaconGroupNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        beaconGroupNamePanel.add(new JLabel("Search by Name:"));
        beaconGroupNamePanel.add(beaconGroupNameField);
        beaconGroupNamePanel.setMinimumSize(beaconGroupNamePanel.getPreferredSize());
        this.add(beaconGroupNamePanel);
    }

    private class GetActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public GetActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response;
            String projectId = projectIdField.getText().trim();
            if (projectId.isEmpty()) {
                return;
            }
            String beaconGroupId = beaconGroupIdField.getText().trim();
            if (beaconGroupId.isEmpty()) {
                response = BeaconGroupManager.getAllGroups(beaconGroupNameField.getText().trim(),
                                                           projectId);
            } else {
                response = BeaconGroupManager.getGroup(beaconGroupId, projectId);
            }
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                String[][] beaconGroupResponse;
                JsonNode responseBody = response.getBody();
                if (responseBody.isArray()) {
                    beaconGroupResponse = BeaconGroupTab.convertBeaconGroupsJsonToTable(responseBody.getArray());
                } else {
                    beaconGroupResponse = BeaconGroupTab.convertBeaconGroupJsonToTable(responseBody.getObject());
                }
                responsePanel.showResponseTable(ItemTable.BEACONGROUPS_TABLE_COL_NAMES, beaconGroupResponse);
            }
        }
    }
}
