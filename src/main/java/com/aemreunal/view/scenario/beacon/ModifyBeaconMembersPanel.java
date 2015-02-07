package com.aemreunal.view.scenario.beacon;

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
import com.aemreunal.view.beacon.BeaconTab;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class ModifyBeaconMembersPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField scenarioIdField;
    private JTextField beaconIdField;
    private JButton    addButton;
    private JButton    removeButton;

    public ModifyBeaconMembersPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        scenarioIdField = new JTextField(5);
        beaconIdField = new JTextField(5);
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

        JPanel beaconIdPanel = new JPanel(new GridBagLayout());
        beaconIdPanel.add(new JLabel("Beacon ID:"));
        beaconIdPanel.add(beaconIdField);
        beaconIdPanel.add(addButton);
        beaconIdPanel.add(removeButton);
        beaconIdPanel.setMaximumSize(beaconIdPanel.getPreferredSize());
        this.add(beaconIdPanel);
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
            String beaconId = beaconIdField.getText().trim();
            if (projectId.isEmpty() || scenarioId.isEmpty() || beaconId.isEmpty()) {
                return;
            }
            HttpResponse<JsonNode> response;
            if (e.getSource().equals(addButton)) {
                response = ScenarioManager.addBeacon(beaconId, scenarioId, projectId);
            } else {
                response = ScenarioManager.removeBeacon(beaconId, scenarioId, projectId);
            }
            responsePanel.showResponseCode(response.getStatus());
            if (response.getStatus() == 200) {
                String[][] beaconResponse = BeaconTab.convertBeaconJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.BEACONS_TABLE_COL_NAMES, beaconResponse);
            }
        }
    }

}
