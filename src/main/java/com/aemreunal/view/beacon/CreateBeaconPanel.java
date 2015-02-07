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

public class CreateBeaconPanel extends JPanel {
    private JTextField uuidField;
    private JTextField majorField;
    private JTextField minorField;
    private JTextField descriptionField;
    private JTextField projectIdField;
    private JButton    createButton;

    public CreateBeaconPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        uuidField = new JTextField(10);
        majorField = new JTextField(10);
        minorField = new JTextField(10);
        descriptionField = new JTextField(10);
        projectIdField = new JTextField(5);
        createButton = new JButton("Create");
        createButton.addActionListener(new CreateActionListener(responsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new GridBagLayout());
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.setMinimumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel uuidPanel = new JPanel(new GridBagLayout());
        uuidPanel.add(new JLabel("UUID:"));
        uuidPanel.add(uuidField);
        uuidPanel.setMinimumSize(uuidPanel.getPreferredSize());
        this.add(uuidPanel);

        JPanel majorPanel = new JPanel(new GridBagLayout());
        majorPanel.add(new JLabel("Major:"));
        majorPanel.add(majorField);
        majorPanel.setMinimumSize(majorPanel.getPreferredSize());
        this.add(majorPanel);

        JPanel minorPanel = new JPanel(new GridBagLayout());
        minorPanel.add(new JLabel("Minor:"));
        minorPanel.add(minorField);
        minorPanel.setMinimumSize(minorPanel.getPreferredSize());
        this.add(minorPanel);

        JPanel descriptionPanel = new JPanel(new GridBagLayout());
        descriptionPanel.add(new JLabel("Description:"));
        descriptionPanel.add(descriptionField);
        descriptionPanel.setMinimumSize(descriptionPanel.getPreferredSize());
        this.add(descriptionPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(createButton);
        buttonPanel.setMinimumSize(buttonPanel.getPreferredSize());
        this.add(buttonPanel);
    }

    private class CreateActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public CreateActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = BeaconManager.createBeacon(uuidField.getText().trim(),
                                                                         majorField.getText().trim(),
                                                                         minorField.getText().trim(),
                                                                         descriptionField.getText().trim(),
                                                                         projectIdField.getText().trim());
            responsePanel.showResponseCode(response.getStatus());
            if (response.getStatus() == 201) {
                // Normal response
                String[][] createResponse = BeaconTab.convertBeaconJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.BEACONS_TABLE_COL_NAMES, createResponse);
            }
        }
    }
}
