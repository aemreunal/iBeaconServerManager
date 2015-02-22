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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.aemreunal.model.RegionManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetRegionPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField regionIdField;
    private JTextField regionNameField;
    private JButton    getButton;

    public GetRegionPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        projectIdField.addActionListener(new GetActionListener(responsePanel));
        regionIdField = new JTextField(5);
        regionIdField.addActionListener(new GetActionListener(responsePanel));
        regionNameField = new JTextField(10);
        regionNameField.addActionListener(new GetActionListener(responsePanel));
        getButton = new JButton("Get");
        getButton.addActionListener(new GetActionListener(responsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.add(new JLabel("Region ID (leave blank to get all):"));
        projectIdPanel.add(regionIdField);
        projectIdPanel.add(getButton);
        projectIdPanel.setMaximumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel regionNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        regionNamePanel.add(new JLabel("Search by Name:"));
        regionNamePanel.add(regionNameField);
        regionNamePanel.setMaximumSize(regionNamePanel.getPreferredSize());
        this.add(regionNamePanel);
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
            String regionId = regionIdField.getText().trim();
            if (regionId.isEmpty()) {
                response = RegionManager.getAllRegions(regionNameField.getText().trim(), projectId);
            } else {
                response = RegionManager.getRegion(regionId, projectId);
            }
            responsePanel.showResponseCode(response.getStatus());
            String[][] regionResponse = null;
            if (response.getStatus() == 200) {
                JsonNode responseBody = response.getBody();
                if (responseBody.isArray()) {
                    regionResponse = RegionTab.convertRegionJsonToTable(responseBody.getArray());
                } else {
                    regionResponse = RegionTab.convertRegionJsonToTable(responseBody.getObject());
                }
            }
            responsePanel.showResponseTable(ItemTable.REGIONS_TABLE_COL_NAMES, regionResponse);
        }
    }
}
