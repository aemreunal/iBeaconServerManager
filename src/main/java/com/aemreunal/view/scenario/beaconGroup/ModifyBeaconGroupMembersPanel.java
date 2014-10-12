package com.aemreunal.view.scenario.beaconGroup;

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
import com.aemreunal.model.ScenarioManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.aemreunal.view.beaconGroup.BeaconGroupTab;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class ModifyBeaconGroupMembersPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField scenarioIdField;
    private JTextField beaconGroupIdField;
    private JButton    addButton;
    private JButton    removeButton;

    public ModifyBeaconGroupMembersPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        scenarioIdField = new JTextField(5);
        beaconGroupIdField = new JTextField(5);
        addButton = new JButton("Add");
        addButton.addActionListener(new ModifyActionListener(responsePanel));
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ModifyActionListener(responsePanel));
    }

    private void addComponents() {
        JPanel idPanel = new JPanel(new GridBagLayout());
        idPanel.add(new JLabel("Project ID:"));
        idPanel.add(projectIdField);
        idPanel.add(new JLabel("Scenario ID:"));
        idPanel.add(scenarioIdField);
        idPanel.setMaximumSize(idPanel.getPreferredSize());
        this.add(idPanel);

        JPanel beaconGroupIdPanel = new JPanel(new GridBagLayout());
        beaconGroupIdPanel.add(new JLabel("Beacon Group ID:"));
        beaconGroupIdPanel.add(beaconGroupIdField);
        beaconGroupIdPanel.add(addButton);
        beaconGroupIdPanel.add(removeButton);
        beaconGroupIdPanel.setMaximumSize(beaconGroupIdPanel.getPreferredSize());
        this.add(beaconGroupIdPanel);
    }

    private class ModifyActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public ModifyActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = projectIdField.getText().trim();
            String scenarioId = scenarioIdField.getText().trim();
            String beaconGroupId = beaconGroupIdField.getText().trim();
            if (projectId.isEmpty() || scenarioId.isEmpty() || beaconGroupId.isEmpty()) {
                return;
            }
            HttpResponse<JsonNode> response;
            if (e.getSource().equals(addButton)) {
                response = ScenarioManager.addBeaconGroup(beaconGroupId, scenarioId, projectId);
            } else {
                response = ScenarioManager.removeBeaconGroup(beaconGroupId, scenarioId, projectId);
            }
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                String[][] beaconGroupResponse = BeaconGroupTab.convertBeaconGroupJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.BEACONGROUPS_TABLE_COL_NAMES, beaconGroupResponse);
            }
        }
    }

}
