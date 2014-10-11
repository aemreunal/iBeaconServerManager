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

public class CreateBeaconGroupPanel extends JPanel {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField projectIdField;
    private JButton    createButton;

    public CreateBeaconGroupPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        nameField = new JTextField(10);
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

        JPanel namePanel = new JPanel(new GridBagLayout());
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        namePanel.setMinimumSize(namePanel.getPreferredSize());
        this.add(namePanel);

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
            String projectId = projectIdField.getText().trim();
            if (projectId.equals("")) {
                return;
            }
            HttpResponse<JsonNode> response = BeaconGroupManager.createGroup(nameField.getText().trim(),
                                                                             descriptionField.getText().trim(),
                                                                             projectId);
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 201) {
                // Normal response
                String[][] createResponse = BeaconGroupTab.convertBeaconGroupJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.BEACONGROUPS_TABLE_COL_NAMES, createResponse);
            }
        }
    }
}
