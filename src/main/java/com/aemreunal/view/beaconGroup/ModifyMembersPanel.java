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

public class ModifyMembersPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField beaconGroupIdField;
    private JTextField beaconIdField;
    private JButton    addButton;
    private JButton    removeButton;

    public ModifyMembersPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        beaconGroupIdField = new JTextField(5);
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
        idPanel.add(new JLabel("Group ID:"));
        idPanel.add(beaconGroupIdField);
        idPanel.setMinimumSize(idPanel.getPreferredSize());
        this.add(idPanel);

        JPanel beaconIdPanel = new JPanel(new GridBagLayout());
        beaconIdPanel.add(new JLabel("Beacon ID:"));
        beaconIdPanel.add(beaconIdField);
        beaconIdPanel.add(addButton);
        beaconIdPanel.add(removeButton);
        beaconIdPanel.setMinimumSize(beaconIdPanel.getPreferredSize());
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
            String groupId = beaconGroupIdField.getText().trim();
            String beaconId = beaconIdField.getText().trim();
            if (projectId.isEmpty() || groupId.isEmpty() || beaconId.isEmpty()) {
                return;
            }
            HttpResponse<JsonNode> response;
            if (e.getSource().equals(addButton)) {
                response = BeaconGroupManager.addMember(beaconId, groupId, projectId);
            } else {
                response = BeaconGroupManager.removeMember(beaconId, groupId, projectId);
            }
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                String[][] beaconResponse = BeaconGroupTab.convertBeaconGroupJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.BEACONGROUPS_TABLE_COL_NAMES, beaconResponse);
            }
        }
    }
}
