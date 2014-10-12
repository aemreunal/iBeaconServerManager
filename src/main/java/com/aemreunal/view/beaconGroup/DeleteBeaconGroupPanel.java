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

public class DeleteBeaconGroupPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField beaconGroupIdField;
    private JButton    deleteButton;

    public DeleteBeaconGroupPanel(final ResponsePanel responsePanel) {
        super(new GridBagLayout());
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        beaconGroupIdField = new JTextField(5);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteBeaconActionListener(responsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Project ID:"));
        this.add(projectIdField);
        this.add(new JLabel("Beacon group ID:"));
        this.add(beaconGroupIdField);
        this.add(deleteButton);
    }

    private class DeleteBeaconActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public DeleteBeaconActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String beaconGroupId = beaconGroupIdField.getText().trim();
            String projectId = projectIdField.getText().trim();
            if (!beaconGroupId.isEmpty() && !projectId.isEmpty()) {
                HttpResponse<JsonNode> response = BeaconGroupManager.deleteGroup(beaconGroupId, projectId);
                responsePanel.showResponseCode(response.getCode());
                if (response.getCode() == 200) {
                    String[][] beaconResponse = BeaconGroupTab.convertBeaconGroupJsonToTable(response.getBody().getObject());
                    responsePanel.showResponseTable(ItemTable.BEACONGROUPS_TABLE_COL_NAMES, beaconResponse);
                }
            }
        }
    }

}
