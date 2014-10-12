package com.aemreunal.view.beacon;

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
import com.aemreunal.model.BeaconManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetBeaconPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField beaconIdField;
    private JTextField beaconUUIDField;
    private JTextField beaconMajorField;
    private JTextField beaconMinorField;
    private JButton    getButton;

    public GetBeaconPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        projectIdField.addActionListener(new GetActionListener(responsePanel));
        beaconIdField = new JTextField(5);
        beaconIdField.addActionListener(new GetActionListener(responsePanel));
        beaconUUIDField = new JTextField(10);
        beaconUUIDField.addActionListener(new GetActionListener(responsePanel));
        beaconMajorField = new JTextField(10);
        beaconMajorField.addActionListener(new GetActionListener(responsePanel));
        beaconMinorField = new JTextField(10);
        beaconMinorField.addActionListener(new GetActionListener(responsePanel));
        getButton = new JButton("Get");
        getButton.addActionListener(new GetActionListener(responsePanel));
    }

    private void addComponents() {
        JPanel idPanel = new JPanel(new GridBagLayout());
        idPanel.add(new JLabel("Project ID:"));
        idPanel.add(projectIdField);
        idPanel.add(new JLabel("Beacon ID (leave blank to get all):"));
        idPanel.add(beaconIdField);
        idPanel.setMaximumSize(idPanel.getPreferredSize());
        this.add(idPanel);

        JPanel projectNamePanel = new JPanel(new GridBagLayout());
        projectNamePanel.add(new JLabel("Search by UUID:"));
        projectNamePanel.add(beaconUUIDField);
        projectNamePanel.setMaximumSize(projectNamePanel.getPreferredSize());
        this.add(projectNamePanel);

        JPanel beaconMajorPanel = new JPanel(new GridBagLayout());
        beaconMajorPanel.add(new JLabel("Search by Major:"));
        beaconMajorPanel.add(beaconMajorField);
        beaconMajorPanel.setMaximumSize(beaconMajorPanel.getPreferredSize());
        this.add(beaconMajorPanel);

        JPanel beaconMinorPanel = new JPanel(new GridBagLayout());
        beaconMinorPanel.add(new JLabel("Search by Minor:"));
        beaconMinorPanel.add(beaconMinorField);
        beaconMinorPanel.setMaximumSize(beaconMinorPanel.getPreferredSize());
        this.add(beaconMinorPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(getButton);
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        this.add(buttonPanel);
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
            if(projectId.isEmpty()) {
                return;
            }
            String beaconId = beaconIdField.getText().trim();
            if (beaconId.isEmpty()) {
                response = BeaconManager.getAllBeacons(beaconUUIDField.getText().trim(),
                                                       beaconMajorField.getText().trim(),
                                                       beaconMinorField.getText().trim(),
                                                       projectId);
            } else {
                response = BeaconManager.getBeacon(beaconId, projectId);
            }
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                String[][] beaconResponse;
                JsonNode responseBody = response.getBody();
                if (responseBody.isArray()) {
                    beaconResponse = BeaconTab.convertBeaconsJsonToTable(responseBody.getArray());
                } else {
                    beaconResponse = BeaconTab.convertBeaconJsonToTable(responseBody.getObject());
                }
                responsePanel.showResponseTable(ItemTable.BEACONS_TABLE_COL_NAMES, beaconResponse);
            }
        }
    }
}
